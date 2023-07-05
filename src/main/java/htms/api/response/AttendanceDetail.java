package htms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import htms.common.constants.AttendanceStatus;
import lombok.*;

import java.util.Date;
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
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Bangkok")
    private Date scheduleDate;
    @JsonProperty("startTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Bangkok")
    private Date scheduleStartTime;
    @JsonProperty("endTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Bangkok")
    private Date scheduleEndTime;
    @JsonProperty("trainer")
    private TrainerResponse scheduleTrainer;
}
