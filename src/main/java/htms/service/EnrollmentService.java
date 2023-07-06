package htms.service;

import htms.api.request.EnrollmentRequest;
import htms.api.response.EnrollmentResponse;
import htms.common.constants.EnrollmentStatus;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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

    EnrollmentResponse update(UUID classId, UUID traineeId, EnrollmentStatus status, String cancelReason);

    @Transactional(rollbackFor = {SQLException.class})
    EnrollmentResponse enrol(EnrollmentRequest request);
}
