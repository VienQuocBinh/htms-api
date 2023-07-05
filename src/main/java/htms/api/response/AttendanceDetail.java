package htms.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import htms.common.constants.AttendanceStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDetail {
    private UUID id;
    private AttendanceStatus status;
    // From schedule
    @JsonProperty("room")
    private RoomResponse scheduleRoom;
}
