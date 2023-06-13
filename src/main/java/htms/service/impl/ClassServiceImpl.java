package htms.service.impl;

import htms.api.domain.CreateClassFormData;
import htms.api.request.ClassRequest;
import htms.api.request.ProgramPerClassRequest;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.api.response.CycleResponse;
import htms.api.response.ProgramResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;
import htms.common.mapper.ClassMapper;
import htms.model.Class;
import htms.model.ClassApproval;
import htms.model.GroupedApprovalStatus;
import htms.repository.ClassApprovalRepository;
import htms.repository.ClassRepository;
import htms.repository.CycleRepository;
import htms.repository.ProgramRepository;
import htms.service.ClassService;
import htms.service.ProgramPerClassService;
import htms.util.ClassUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ModelMapper modelMapper;
    private final ClassRepository classRepository;
    private final ClassApprovalRepository classApprovalRepository;
    private final ClassMapper classMapper;
    private final ClassUtil classUtil;
    private final ProgramRepository programRepository;
    private final CycleRepository cycleRepository;
    private ProgramPerClassService programPerClassService;

    // DI using setter to avoid circular dependency
    @Autowired
    public void setProgramPerClassService(@Lazy ProgramPerClassService programPerClassService) {
        this.programPerClassService = programPerClassService;
    }

    @Override
    public ClassResponse createClass(ClassRequest request) {
        // create the class
        // todo: generate class code, assign created by
        var clazz = Class.builder()
                .name(request.getName())
                .code(classUtil.generateClassCode(request.getProgramId(), request.getStartDate()))
                .createdBy(UUID.randomUUID())
                .generalSchedule(request.getGeneralSchedule())
                .quantity(0)
                .build();
        classRepository.save(clazz);

        // create program per class
        var programPerClass = programPerClassService.create(ProgramPerClassRequest.builder()
                .programId(request.getProgramId())
                .classId(clazz.getId())
                .cycleId(request.getCycleId())
                .programStartDate(request.getStartDate())
                .programEndDate(request.getEndDate())
                .minQuantity(request.getMinQuantity())
                .maxQuantity(request.getMaxQuantity())
                .build());
        // create class approval with PENDING status
        // todo: assign created by
        var classApproval = ClassApproval.builder()
                .clazz(clazz)
                .status(ClassApprovalStatus.PENDING)
                .build();
        classApprovalRepository.save(classApproval);

        return classMapper.toResponse(clazz, classApproval, programPerClass);
    }

    @Override
    public List<ClassResponse> getClasses() {
        return classRepository.findAll()
                .stream().map((element) -> modelMapper.map(element, ClassResponse.class))
                .toList();
    }

    @Override
    public CreateClassFormData getCreateClassFormData() {
        var programs = programRepository.findAll();
        var cycles = cycleRepository.findAll();
        return CreateClassFormData.builder()
                .programs(programs.stream()
                        .map((element) -> modelMapper.map(
                                element,
                                ProgramResponse.class)).toList())
                .cycles(cycles.stream()
                        .map((element) -> modelMapper.map(
                                element,
                                CycleResponse.class)).toList())
                .build();
    }

    @Override
    public ClassResponse getClassDetail(UUID id) {
        // todo: handle exceptions
        return modelMapper.map(
                classRepository.findById(id).orElseThrow(),
                ClassResponse.class);
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
        return classes.stream().map(aClass -> {
            var model = modelMapper.map(aClass, ClassesApprovalResponse.class);
            var approvalStatus = finalApprovals.stream().filter(classApproval -> classApproval.getClazz().getId().equals(aClass.getId())).findFirst();
            model.setProgramCode(aClass.getCode());
            model.setStatus(approvalStatus.get().getStatus());
            return model;
        }).toList();
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
