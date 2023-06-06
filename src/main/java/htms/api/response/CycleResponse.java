package htms.api.response;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CycleResponse {
    private UUID id;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date vacationStartDate;
    private Date vacationEndDate;
}
