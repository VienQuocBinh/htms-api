package htms.service;

import htms.api.response.TraineeResponse;

import java.util.List;
import java.util.UUID;

public interface TraineeService {
    List<TraineeResponse> getTraineesByClassId(UUID classId, UUID programId);
}
