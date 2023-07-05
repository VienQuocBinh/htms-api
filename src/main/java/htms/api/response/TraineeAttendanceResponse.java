package htms.api.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeAttendanceResponse {
    private UUID programId;
    private String programCode;
    private String programName;
    // Class details
    private UUID classId;
    private String className;
    private String classCode;
    // Attendance detail
    private List<AttendanceDetail> attendances;
}
