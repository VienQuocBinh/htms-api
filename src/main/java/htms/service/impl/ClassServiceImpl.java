package htms.service.impl;

import htms.api.domain.CreateClassFormData;
import htms.api.domain.OverlappedSchedule;
import htms.api.request.*;
import htms.api.response.ClassApprovalResponse;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.api.response.EnrollmentResponse;
import htms.common.constants.*;
import htms.model.Class;
import htms.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;
    private final ClassUtil classUtil;
    private TraineeService traineeService;
    private TrainerService trainerService;
    private ProgramService programService;
    private CycleService cycleService;
    private EnrollmentService enrollmentService;
    private ClassApprovalService classApprovalService;
    private ProfileService profileService;
    private ScheduleService scheduleService;
    private AttendanceService attendanceService;

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
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setClassApprovalService(ClassApprovalService classApprovalService) {
        this.classApprovalService = classApprovalService;
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Autowired
    public void setAttendanceService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public ClassResponse createClass(ClassRequest request) {
        boolean hasOverlap = false;
        // Check the schedule of trainer
        OverlappedSchedule overlappedScheduleOfTrainer = trainerService.getOverlappedScheduleOfTrainer(
                request.getTrainerId(),
                request.getGeneralSchedule());

        // Check the schedule of trainees
        List<OverlappedSchedule> traineeOverlappedSchedules = new ArrayList<>();
        if (!overlappedScheduleOfTrainer.getOverlappedDayTimes().isEmpty()) {
            var trainer = trainerService.getTrainer(request.getTrainerId());
            overlappedScheduleOfTrainer.setName(trainer.getName());
            overlappedScheduleOfTrainer.setCode(trainer.getCode());
            traineeOverlappedSchedules.add(overlappedScheduleOfTrainer);
            hasOverlap = true;
        }

        for (UUID traineeId : request.getTraineeIds()) {
            OverlappedSchedule overlappedScheduleOfTrainee = traineeService.getOverlappedScheduleOfTrainee(traineeId, request.getGeneralSchedule());
            if (!overlappedScheduleOfTrainee.getOverlappedDayTimes().isEmpty()) {
                var trainee = traineeService.getTrainee(traineeId);
                overlappedScheduleOfTrainee.setName(trainee.getName());
                overlappedScheduleOfTrainee.setCode(trainee.getCode());
                traineeOverlappedSchedules.add(overlappedScheduleOfTrainee);
                hasOverlap = true;
            }
        }

        if (hasOverlap) {
            return ClassResponse.builder()
                    .overlappedSchedules(traineeOverlappedSchedules)
                    .build();
        }

        // create the class
        // todo: assign created by
        var clazz = Class.builder()
                .name(request.getName())
                .code(classUtil.generateClassCode(request.getProgramId(), request.getStartDate()))
                .createdBy(UUID.randomUUID())
                .generalSchedule(request.getGeneralSchedule())
                .quantity(request.getQuantity())
                .minQuantity(request.getMinQuantity())
                .maxQuantity(request.getMaxQuantity())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getNotRegistered() ? ClassStatus.OPENING : ClassStatus.PLANNING)
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

        // Check the notRegister status. True: open class immediately, False: allow to enroll
        if (request.getNotRegistered()) {
            // create class approval with APPROVE_FOR_OPENING status
            classApprovalService.create(
                    ApprovalRequest.builder()
                            .comment("Lớp đã được mở thành công")
                            .id(clazz.getId())
                            .dueDate(request.getDueDate())
                            .build(),
                    ClassApprovalStatus.APPROVE_FOR_OPENING);

            // Assign trainees to the class, create enrollment status APPROVE
            // Update trainees profile
            request.getTraineeIds().forEach(traineeId -> {
                enrollmentService.create(EnrollmentRequest.builder()
                        .classId(clazz.getId())
                        .traineeId(traineeId)
                        .build(), EnrollmentStatus.APPROVE);

                UUID profileId = traineeService.getTrainee(traineeId).getProfile().getId();
                profileService.updateProfile(ProfileUpdateRequest.builder()
                        .id(profileId)
                        .status(ProfileStatus.STUDYING)
                        .build());
            });

            // Create schedule, find the suitable room
            scheduleService.createSchedulesOfClass(
                    clazz.getId(),
                    clazz.getProgram().getId(),
                    clazz.getTrainer().getId(),
                    UUID.fromString("b49d2b9c-d8a1-473d-bafe-2207f62a034b"),
                    clazz.getStartDate(),
                    clazz.getEndDate(), clazz.getGeneralSchedule());

            // Create attendances for the schedule
            attendanceService.createAttendancesOfClass(clazz.getId());
        } else {
            // create class approval with PENDING status
            classApprovalService.create(
                    ApprovalRequest.builder()
                            .comment("Lớp đang chờ duyệt")
                            .id(clazz.getId())
                            .dueDate(request.getDueDate())
                            .build(),
                    ClassApprovalStatus.PENDING);
            // Assign trainees to the class, create enrollment status PENDING
            request.getTraineeIds().forEach(traineeId -> enrollmentService.create(EnrollmentRequest.builder()
                    .classId(clazz.getId())
                    .traineeId(traineeId)
                    .build(), EnrollmentStatus.PENDING));
        }

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
        var response = modelMapper.map(clazz, ClassResponse.class);
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

        switch (status) {
            case APPROVE_FOR_PUBLISHING -> clazz.setStatus(ClassStatus.PENDING);
            case APPROVE_FOR_OPENING -> {
                clazz.setStatus(ClassStatus.OPENING);
                List<EnrollmentResponse> enrollmentByClassIdAndStatus = enrollmentService.getEnrollmentByClassIdAndStatus(clazz.getId(), EnrollmentStatus.PENDING);
                for (EnrollmentResponse byClassIdAndStatus : enrollmentByClassIdAndStatus) {
                    var traineeId = byClassIdAndStatus.getEnrollmentIdTrainee().getId();
                    // update enrollment status to APPROVE
                    enrollmentService.update(clazz.getId(),
                            traineeId,
                            EnrollmentStatus.APPROVE,
                            null);
                    // update profile
                    UUID profileId = traineeService.getTrainee(traineeId).getProfile().getId();
                    profileService.updateProfile(ProfileUpdateRequest.builder()
                            .id(profileId)
                            .status(ProfileStatus.STUDYING)
                            .build());
                }

                // Create schedule, find the suitable room
                scheduleService.createSchedulesOfClass(
                        clazz.getId(),
                        clazz.getProgram().getId(),
                        clazz.getTrainer().getId(),
                        UUID.fromString("b49d2b9c-d8a1-473d-bafe-2207f62a034b"),
                        clazz.getStartDate(),
                        clazz.getEndDate(), clazz.getGeneralSchedule());

                // Create attendances for the schedule
                attendanceService.createAttendancesOfClass(clazz.getId());
            }
            case REJECT_FOR_OPENING, REJECT_FOR_PUBLISHING -> {
                clazz.setStatus(ClassStatus.REJECT);
                List<EnrollmentResponse> enrollmentByClassIdAndStatus = enrollmentService.getEnrollmentByClassIdAndStatus(clazz.getId(), EnrollmentStatus.PENDING);
                for (EnrollmentResponse byClassIdAndStatus : enrollmentByClassIdAndStatus) {
                    var traineeId = byClassIdAndStatus.getEnrollmentIdTrainee().getId();
                    // update enrollment status to REJECT
                    enrollmentService.update(clazz.getId(),
                            traineeId,
                            EnrollmentStatus.REJECT,
                            request.getComment());
                }
            }
        }
        classRepository.save(clazz);
        return classApprovalService.create(request, status);
    }

    @Override
    public CreateClassFormData initCreateClassFormData() {
        var createClassFormData = CreateClassFormData.builder();
        createClassFormData.programs(programService.getPrograms())
                .trainers(trainerService.getTrainers())
                .cycles(cycleService.getCycles());
        return createClassFormData.build();
    }

    @Override
    public List<ClassResponse> getClassesOfTrainer(UUID trainerId) {
        return classRepository.findAllByTrainer_Id(trainerId)
                .orElse(List.of())
                .parallelStream()
                .map((element) -> modelMapper.map(element, ClassResponse.class))
                .toList();
    }

    @Override
    public List<ClassResponse> getClassByProgramId(UUID programId, ClassStatus status) {
        return classRepository.findAllByProgram_IdAndStatusEquals(programId, status)
                .orElse(List.of())
                .parallelStream()
                .map((element) -> modelMapper.map(element, ClassResponse.class))
                .toList();
    }

    @Override
    public List<Class> getAllCurrentTakingClassesByTrainee(UUID id) {
        return classRepository.findAllCurrentTakingClassesByTrainee(id)
                .orElse(List.of());
    }

    @Override
    public ClassResponse update(ClassUpdateRequest request) {
        // todo: handle exception
        var clazz = classRepository.findById(request.getId())
                .orElseThrow(EntityNotFoundException::new);
//        clazz.setName(request.getName());
        clazz.setQuantity(request.getQuantity());

        classRepository.save(clazz);
        return modelMapper.map(clazz, ClassResponse.class);
    }
}
