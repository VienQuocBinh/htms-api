package htms.api.response;

import htms.common.constants.AttendanceStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeAttendanceResponse {
    private UUID id;
    private AttendanceStatus status;
    private ScheduleResponse schedule;
}
