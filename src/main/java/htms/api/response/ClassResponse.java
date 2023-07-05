package htms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import htms.api.domain.OverlappedSchedule;
import htms.common.constants.ClassStatus;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassResponse {
    private UUID id;
    private String name;
    private String code;
    private String generalSchedule;
    private UUID createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date createdDate;
    private Integer quantity;
    private Integer minQuantity;
    private Integer maxQuantity;
    private ClassStatus status;
    private TrainerResponse trainer;
    private ProgramResponse program;
    private CycleResponse cycle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Bangkok")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy ", timezone = "Asia/Bangkok")
    private Date endDate;
    // Additional properties
    private List<TraineeResponse> trainees;
    private List<OverlappedSchedule> overlappedSchedules;
}
