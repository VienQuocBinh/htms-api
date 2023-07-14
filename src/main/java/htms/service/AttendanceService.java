package htms.service;

import htms.api.response.AttendanceDetail;
import htms.api.response.TraineeAttendanceResponse;

import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    /**
     * Create attendances for a given class with NOT_START status
     *
     * @param classId {@link UUID}
     */
    void createAttendancesOfClass(UUID classId);

    List<TraineeAttendanceResponse> getAttendancesByClassId(UUID classId);

    List<TraineeAttendanceResponse> getAttendancesByTraineeId(UUID traineeId);

    AttendanceDetail getAttendanceByTraineeIdAndScheduleId(UUID traineeId, UUID scheduleId);
}
