package htms.service;

import htms.api.request.EnrollmentRequest;
import htms.api.response.EnrollmentResponse;
import htms.common.constants.EnrollmentStatus;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<EnrollmentResponse> getEnrollmentByClassIdAndStatus(UUID classId, EnrollmentStatus status);

    EnrollmentResponse create(EnrollmentRequest request);

    List<EnrollmentResponse> updateEnrollmentStatusByClassId(UUID classId, UUID traineeId, EnrollmentStatus status);
}
