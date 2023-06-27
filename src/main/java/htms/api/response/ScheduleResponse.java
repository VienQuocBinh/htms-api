package htms.api.response;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private UUID id;
    private Date startTime;
    private Date endTime;
    private RoomResponse room;
    private ClassResponse clazz;
    private TraineeResponse trainee;
}
