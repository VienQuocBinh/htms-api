package htms.api.request;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramPerClassRequest {
    private UUID programId;
    private UUID classId;
    private UUID cycleId;
    private Date programStartDate;
    private Date programEndDate;
    private Integer minQuantity;
    private Integer maxQuantity;
}
