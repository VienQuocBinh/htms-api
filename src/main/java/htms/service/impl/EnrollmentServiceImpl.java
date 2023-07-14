package htms.service.impl;

import htms.api.request.ClassUpdateRequest;
import htms.api.request.EnrollmentRequest;
import htms.api.response.EnrollmentResponse;
import htms.common.constants.ClassStatus;
import htms.common.constants.EnrollmentStatus;
import htms.common.exception.EnrollmentProcessException;
import htms.common.exception.EntityNotFoundException;
import htms.model.Class;
import htms.model.Enrollment;
import htms.model.Trainee;
import htms.model.embeddedkey.EnrollmentId;
import htms.repository.EnrollmentRepository;
import htms.service.ClassService;
import htms.service.EnrollmentService;
import htms.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper modelMapper;
    private final TraineeService traineeService;
    private ClassService classService;

    @Autowired
    public void setClassService(@Lazy ClassService classService) {
        this.classService = classService;
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentByClassIdAndStatus(UUID classId, EnrollmentStatus status) {
        return enrollmentRepository
                .findAllByClassIdAndStatus(classId, status)
                .orElse(List.of())
                .stream()
                .map((element) -> modelMapper.map(element, EnrollmentResponse.class))
                .toList();
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public EnrollmentResponse create(EnrollmentRequest request, EnrollmentStatus status) {
        var id = EnrollmentId.builder()
                .clazz(Class.builder()
                        .id(request.getClassId())
                        .build())
                .trainee(Trainee.builder()
                        .id(request.getTraineeId())
                        .build())
                .build();
        return modelMapper
                .map(enrollmentRepository.save(Enrollment.builder()
                        .id(id)
                        .enrollmentDate(new Date())
                        .status(status)
                        .build()), EnrollmentResponse.class);
    }

    @Override
    public EnrollmentResponse update(UUID classId, UUID traineeId, EnrollmentStatus status, String cancelReason) {
        var id = EnrollmentId.builder()
                .clazz(Class.builder()
                        .id(classId)
                        .build())
                .trainee(Trainee.builder()
                        .id(traineeId)
                        .build())
                .build();
        var enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Enrollment.class, "id", id));
        enrollment.setStatus(status);
        enrollment.setCancelReason(cancelReason);
        enrollmentRepository.save(enrollment);
        return modelMapper.map(enrollment, EnrollmentResponse.class);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public EnrollmentResponse enrol(EnrollmentRequest request) {
        var clazz = classService.getClassDetail(request.getClassId());
        // Throw exception whether the class is not available (PENDING) to enrol
        if (!clazz.getStatus().equals(ClassStatus.PENDING)) {
            throw new EnrollmentProcessException("The class is not available");
        } else if (clazz.getQuantity() >= clazz.getMaxQuantity()) {
            throw new EnrollmentProcessException(MessageFormat.format("The quantity of the class is maximum ({0}/{1})", clazz.getQuantity(), clazz.getMaxQuantity()));
        }

        // Check overlap schedule of the trainee
        var overlappedScheduleOfTrainee = traineeService.getOverlappedScheduleOfTrainee(request.getTraineeId(), clazz.getGeneralSchedule());

        if (!overlappedScheduleOfTrainee.getOverlappedDayTimes().isEmpty()) {
            return EnrollmentResponse.builder()
                    .overlappedSchedule(overlappedScheduleOfTrainee)
                    .build();
        }
        // Update quantity of the class
        int quantity = traineeService.getTraineesByClassId(request.getClassId()).size();
        classService.update(ClassUpdateRequest.builder()
                .id(clazz.getId())
                .quantity(quantity + 1)
                .build());

        return create(request, EnrollmentStatus.PENDING);
    }
}
