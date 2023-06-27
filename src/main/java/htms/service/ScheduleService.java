package htms.service;

import java.util.Date;
import java.util.UUID;

public interface ScheduleService {
    void createSchedulesOfClass(UUID classId, UUID trainerId, UUID roomId, String generalSchedule, Date startDate, Date endDate);
}
