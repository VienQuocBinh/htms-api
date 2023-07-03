package htms.service.impl;

import htms.api.request.EnrollmentRequest;
import htms.api.response.EnrollmentResponse;
import htms.common.constants.EnrollmentStatus;
import htms.model.Class;
import htms.model.Enrollment;
import htms.model.Trainee;
import htms.model.embeddedkey.EnrollmentId;
import htms.repository.EnrollmentRepository;
import htms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper modelMapper;

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
    public EnrollmentResponse create(EnrollmentRequest request) {
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
                        .status(EnrollmentStatus.PENDING)
                        .build()), EnrollmentResponse.class);
    }
}
