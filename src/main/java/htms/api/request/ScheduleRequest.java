package htms.api.request;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {
    private Date date;
    private Date startTime;
    private Date endTime;
    private UUID roomId;
    private UUID classId;
    private UUID trainerId;
}
