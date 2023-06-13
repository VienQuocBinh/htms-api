package htms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import htms.common.constants.ClassApprovalStatus;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponse {
    private UUID id;
    private String name;
    private String code;
    private String generalSchedule;
    private UUID createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date createdDate;
    // program per class
    private ClassApprovalStatus status;
    private Integer minQuantity;
    private Integer maxQuantity;
    private UUID programId;
    private UUID cycleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Bangkok")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy ", timezone = "Asia/Bangkok")
    private Date endDate;
}
