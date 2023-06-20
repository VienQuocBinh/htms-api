package htms.api.request;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {
    private String name;
    private String generalSchedule;
    @org.hibernate.validator.constraints.UUID
    private UUID departmentId;
    @org.hibernate.validator.constraints.UUID
    private UUID trainerId;
    @org.hibernate.validator.constraints.UUID
    private UUID programId;
    @org.hibernate.validator.constraints.UUID
    private UUID cycleId;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Date startDate;
    private Date endDate;
}
