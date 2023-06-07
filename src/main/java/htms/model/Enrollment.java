package htms.model;

import htms.model.embeddedkey.EnrollmentId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment implements Serializable {
    @EmbeddedId
    private EnrollmentId id;
    @NotNull
    private Date enrollmentDate;
    private boolean isCancelled;
    private String cancelReason;
}
