package htms.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {
    @NotNull(message = "Name not null")
    private String name;
    private String generalSchedule;
    private UUID departmentId;
    private UUID trainerId;
    private UUID programId;
    private UUID cycleId;
    //    private UUID roomId;
    private Integer quantity;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Date startDate;
    private Date endDate;
    // Additional properties
    private List<UUID> traineeIds; // request trainees to assign to the class
    private boolean notRegistered; // true: open class directly, false: allow for trainees to enroll
    private Date dueDate; // Deadline for making approval of the class
}
