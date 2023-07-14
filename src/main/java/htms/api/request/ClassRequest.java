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
    @NotNull(message = "Class name must not be null")
    private String name;
    @NotNull
    private String generalSchedule;
    @NotNull
    private UUID departmentId;
    @NotNull
    private UUID trainerId;
    @NotNull
    private UUID programId;
    @NotNull
    private UUID cycleId;
    //    private UUID roomId;
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer minQuantity;
    @NotNull
    private Integer maxQuantity;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    // Additional properties
    private List<UUID> traineeIds; // request trainees to assign to the class
    @NotNull
    private Boolean notRegistered; // true: open class directly, false: allow for trainees to enroll
    @NotNull
    private Date dueDate; // Deadline for making approval of the class
}
