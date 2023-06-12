package htms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import htms.common.constants.ClassStatus;
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
    private ClassStatus status;
    private Integer minQuantity;
    private Integer maxQuantity;
    private String code;
    private String reason;
    private UUID programId;
    private UUID cycleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date endDate;
//    private UUID trainerId;
}
