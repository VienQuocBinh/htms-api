package htms.service;

import htms.api.response.ScheduleResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    void createSchedulesOfClass(UUID classId, UUID trainerId, UUID roomId, String generalSchedule, Date startDate, Date endDate);

    List<ScheduleResponse> getScheduleOfClass(UUID id);

    List<ScheduleResponse> getScheduleOfTrainer(UUID id);

    List<ScheduleResponse> getScheduleOfTrainee(UUID id);
}
