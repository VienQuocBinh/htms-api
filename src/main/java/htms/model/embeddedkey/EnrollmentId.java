package htms.model.embeddedkey;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class EnrollmentId implements Serializable {
    private UUID programId;
    private UUID cycleId;
    private UUID traineeID;
}
