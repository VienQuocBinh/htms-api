package htms.service;

import htms.api.response.PageResponse;
import htms.api.response.TraineeResponse;

import java.util.List;
import java.util.UUID;

public interface TraineeService {
    List<TraineeResponse> getTraineesByClassId(UUID classId);

    //    PageResponse<TraineeResponse> getTraineesPage(int page, int size, String filterOr, String filterAnd, String orders);
    PageResponse<TraineeResponse> getTraineesPage(int page, int size, String q, String title, UUID departmentId, String orders);

    TraineeResponse getTrainee(UUID traineeId);
}
