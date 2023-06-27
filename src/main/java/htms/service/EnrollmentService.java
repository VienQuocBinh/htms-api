package htms.service;

import htms.api.request.EnrollmentRequest;
import htms.api.response.EnrollmentResponse;
import htms.common.constants.EnrollmentStatus;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<EnrollmentResponse> getEnrollmentByClassIdAndStatus(UUID classId, EnrollmentStatus status);

    /**
     * Create new Enrollment with APPROVE status
     *
     * @param request {@link EnrollmentRequest}
     * @return {@link EnrollmentResponse}
     */
    EnrollmentResponse create(EnrollmentRequest request);
}
