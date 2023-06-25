package htms.service.impl;

import htms.api.domain.CreateClassFormData;
import htms.api.request.ApprovalRequest;
import htms.api.request.ClassRequest;
import htms.api.request.EnrollmentRequest;
import htms.api.request.ProfileUpdateRequest;
import htms.api.response.ClassApprovalResponse;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.ProfileStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;
import htms.model.Class;
import htms.model.*;
import htms.repository.ClassApprovalRepository;
import htms.repository.ClassRepository;
import htms.service.*;
import htms.util.ClassUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
    private final ClassUtil classUtil;
    private TraineeService traineeService;
    private TrainerService trainerService;
    private ProgramService programService;
    private CycleService cycleService;
    private EnrollmentService enrollmentService;
    private ClassApprovalService classApprovalService;
    private ProfileService profileService;

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Autowired
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    @Autowired
    public void setCycleService(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setEnrollmentService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Autowired
    public void setClassApprovalService(ClassApprovalService classApprovalService) {
        this.classApprovalService = classApprovalService;
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public ClassResponse createClass(ClassRequest request) {
        // create the class
        // todo: assign created by
        var clazz = Class.builder()
                .name(request.getName())
                .code(classUtil.generateClassCode(request.getProgramId(), request.getStartDate()))
                .createdBy(UUID.randomUUID())
                .generalSchedule(request.getGeneralSchedule())
                .quantity(request.getQuantity())
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
        classApprovalService.create(
                ApprovalRequest.builder()
                        .comment("Lớp đang chờ duyệt")
                        .id(clazz.getId())
                        .build(),
                ClassApprovalStatus.PENDING);
        // Assign trainees to the class, create enrollment status APPROVE, update profile status STUDYING
        request.getTraineeIds().forEach(traineeId -> {
            enrollmentService.create(EnrollmentRequest.builder()
                    .classId(clazz.getId())
                    .traineeId(traineeId)
                    .build());
            UUID profileId = traineeService.getTrainee(traineeId).getProfile().getId();
            profileService.updateProfile(ProfileUpdateRequest.builder()
                    .id(profileId)
                    .status(ProfileStatus.STUDYING)
                    .build());
        });

        // todo: generate schedule based on generalSchedule

        ClassResponse response = modelMapper.map(clazz, ClassResponse.class);
        // Set list of trainees of a class
        response.setTrainees(traineeService.getTraineesByClassId(clazz.getId()));
        return response;
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
        var classApproval = classApprovalRepository
                .getClassApprovalByClazzIdOrderByCreatedDateDesc(clazz.getId())
                .orElseThrow(EntityNotFoundException::new);
        var response = modelMapper.map(clazz, ClassResponse.class);
        response.setStatus(classApproval.getStatus());
        // Get list of trainees in the class
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
        var approvals = classApprovalService.getLatestClassApprovals();
        approvals = approvals.stream().filter(classApproval -> classApproval.getStatus().equals(status)).toList();
        Set<UUID> approvalsClassIdSet = approvals.stream().map(classApproval -> classApproval.getClazz().getId()).collect(Collectors.toSet());
        classes = classes.stream().filter(aClass -> (aClass.getCode().contains(q) || aClass.getName().contains(q)) && approvalsClassIdSet.contains(aClass.getId())).toList();
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

        return classApprovalService.create(
                ApprovalRequest.builder()
                        .comment(request.getComment())
                        .id(clazz.getId())
                        .build(),
                status);
    }

    @Override
    public CreateClassFormData initCreateClassFormData() {
        var createClassFormData = CreateClassFormData.builder();
        createClassFormData.programs(programService.getPrograms())
                .trainers(trainerService.getTrainers())
                .cycles(cycleService.getCycles());
        return createClassFormData.build();
    }
}
