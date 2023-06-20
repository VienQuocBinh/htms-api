package htms.service;

import htms.api.response.TraineeResponse;
import htms.common.constants.EnrollmentStatus;

import java.util.List;
import java.util.UUID;

public interface TraineeService {
    List<TraineeResponse> getTraineesByClassId(UUID classId, EnrollmentStatus status);

    List<TraineeResponse> getTrainees();
}
