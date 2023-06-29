package htms.api.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {
    private UUID scheduleId;
    private UUID traineeId;
}
