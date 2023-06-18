package htms.service.impl;

import htms.api.request.ApprovalRequest;
import htms.api.request.ClassRequest;
import htms.api.response.ClassApprovalResponse;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.api.response.ProgramResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;
import htms.common.mapper.ClassMapper;
import htms.model.Class;
import htms.model.*;
import htms.repository.ClassApprovalRepository;
import htms.repository.ClassRepository;
import htms.repository.ProgramRepository;
import htms.service.ClassService;
import htms.service.TraineeService;
import htms.util.ClassUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final ClassApprovalRepository classApprovalRepository;
    private final ModelMapper modelMapper;
    private final ClassMapper classMapper;
    private final ClassUtil classUtil;
    private final ProgramRepository programRepository;
    private TraineeService traineeService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public ClassResponse createClass(ClassRequest request) {
        // create the class
        // todo: generate class code, assign created by
        var clazz = Class.builder()
                .name(request.getName())
                .code(classUtil.generateClassCode(request.getProgramId(), request.getStartDate()))
                .createdBy(UUID.randomUUID())
                .generalSchedule(request.getGeneralSchedule())
                .quantity(0)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .trainer(Trainer.builder()
                        .id(request.getTrainerId())
                        .build())
                .cycle(Cycle.builder()
                        .id(request.getCycleId())
                        .build())
                .program(Program.builder()
                        .id(request.getProgramId())
                        .build())
                .build();
        classRepository.save(clazz);

        // create class approval with PENDING status
        // todo: assign created by
        var currentLatestId = classApprovalRepository.getLatestId();
        var classApproval = ClassApproval.builder()
                .status(ClassApprovalStatus.PENDING)
                .clazz(clazz);
        // if the data table is already has data, then create with id = latestId + 1
        if (currentLatestId.isPresent()) {
            Long currentId = (Long) entityManager.createNativeQuery("SELECT setval('class_approval_id_seq', " + currentLatestId.get() + ", true);")
                    .getSingleResult();
            classApproval.id(currentId + 1);
        }
        classApprovalRepository.save(classApproval.build());

        return classMapper.toResponse(clazz, classApproval.build());
    }

    @Override
    public List<ClassResponse> getClasses() {
        return classRepository.findAll()
                .stream().map((element) -> modelMapper.map(element, ClassResponse.class))
                .toList();
    }

    @Override
    public ClassResponse getClassDetail(UUID id) {
        // todo: handle exceptions
        var clazz = classRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        var program = programRepository.findById(clazz.getProgram().getId())
                .orElseThrow();
        var classApproval = classApprovalRepository.getClassApprovalByClazzIdOrderByCreatedDateDesc(clazz.getId()).orElseThrow(EntityNotFoundException::new);
        var response = modelMapper.map(clazz, ClassResponse.class);
        response.setProgram(modelMapper.map(program, ProgramResponse.class));
        response.setStatus(classApproval.getStatus());
        response.setTrainees(traineeService.getTraineesByClassId(id));
        return response;
    }

    @Override
    public List<ClassesApprovalResponse> searchClasses(String q, ClassApprovalStatus status, SortBy sortBy, SortDirection direction) {
        List<Class> classes = List.of();
        if (sortBy.equals(SortBy.CREATED_DATE)) {
            classes = direction == SortDirection.DESC ? classRepository.findAllByOrderByCreatedDateDesc() : classRepository.findAllByOrderByCreatedDateAsc();
        }
        if (classes.isEmpty()) {
            return List.of();
        }
        var approvals = getLatestClassApprovals();
        approvals = approvals.stream().filter(classApproval -> classApproval.getStatus().equals(status)).toList();
        Set<UUID> approvalsClassIdSet = approvals.stream().map(classApproval -> classApproval.getClazz().getId()).collect(Collectors.toSet());
        classes = classes.stream().filter(aClass -> (aClass.getCode().contains(q) || aClass.getName().contains(q)) && approvalsClassIdSet.contains(aClass.getId())).toList();
//        var programPerClass = classes.stream().map(aClass -> programPerClassRepository.findAllById_Clazz_Id(aClass.getId()))
        List<ClassApproval> finalApprovals = approvals;
        // todo: handle exceptions
        return classes.stream().map(aClass -> {
            var model = modelMapper.map(aClass, ClassesApprovalResponse.class);
            var approvalStatus = finalApprovals.stream().filter(classApproval -> classApproval.getClazz().getId().equals(aClass.getId())).findFirst()
                    .orElseThrow(EntityNotFoundException::new);
            model.setProgramCode(aClass.getCode());
            model.setStatus(approvalStatus.getStatus());
            return model;
        }).toList();
    }

    @Override
    public ClassApprovalResponse makeApproval(ApprovalRequest request, ClassApprovalStatus status) {
        var clazz = classRepository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        var newApproval = ClassApproval.builder()
                .clazz(clazz)
                //TODO: replace hardcode UUID to real ID getFrom client
                .createdBy(UUID.fromString("3bdb9fdd-40a0-4bd4-93aa-3462c2164d08"))
                .createdDate(Date.from(Instant.now()))
                .comment(request.getComment())
                .status(status)
                .build();
        var savedApproval = classApprovalRepository.save(newApproval);
        var response = modelMapper.map(savedApproval, ClassApprovalResponse.class);
        response.setId(clazz.getId());
        return response;
    }

    private List<ClassApproval> getLatestClassApprovals() {
        //group approvals by class_id and get latest created_date for each class_id
        var groupedMaxClassApprovals = classApprovalRepository.getLatestClassApprovalsGroupedByClazzId();
        var classIdGroup = groupedMaxClassApprovals.stream().map(GroupedApprovalStatus::getClassId).toList();
        var dateTimeGroup = groupedMaxClassApprovals.stream().map(GroupedApprovalStatus::getFilteredDate).toList();
        //filter the table get the approvals from the list above
        return classApprovalRepository.findAll().stream()
                .filter(classApproval -> classIdGroup.contains(classApproval.getClazz().getId()) && dateTimeGroup.contains(classApproval.getCreatedDate()))
                .toList();
    }


}
