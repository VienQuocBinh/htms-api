package htms.service;

import htms.api.domain.OverlappedSchedule;
import htms.api.response.PageResponse;
import htms.api.response.TraineeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TraineeService {
    /**
     * Get the trainees of a class who have the enrollment status is APPROVE
     *
     * @param classId {@link UUID}
     * @return {@code List<TraineeResponse>}
     */
    List<TraineeResponse> getTraineesByClassId(UUID classId);

    //    PageResponse<TraineeResponse> getTraineesPage(int page, int size, String filterOr, String filterAnd, String orders);
    PageResponse<TraineeResponse> getTraineesPage(int page, int size, String q, String title, UUID departmentId, String orders);

    List<TraineeResponse> saveTraineesFromFile(MultipartFile file);

    OverlappedSchedule getOverlappedScheduleOfTrainee(UUID id, String generalSchedule);

    TraineeResponse getTrainee(UUID id);
}
