package htms.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import htms.common.constants.EnrollmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    @JsonProperty("trainee")
    private TraineeResponse enrollmentIdTrainee;
    @JsonProperty("class")
    private ClassResponse enrollmentIdClazz;
    private Date enrollmentDate;
    private Boolean isCancelled;
    private String cancelReason;
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}
