package htms.model;

import htms.common.constants.EnrollmentStatus;
import htms.model.embeddedkey.EnrollmentId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Boolean isCancelled;
    private String cancelReason;
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}
