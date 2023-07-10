package htms.api.request;

import htms.common.constants.GradingMethod;
import htms.common.constants.TimeUnit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.cfg.defs.UUIDDef;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuizRequest {
    private UUID id;
    @NotNull(message = "name must not be null")
    private String name;
    private String description;
    private Date openTime;
    private Date closeTime;
    @Min(value = 0, message = "Time limit cannot lower than 0")
    private Integer timeLimit;
    @NotNull(message = "must be one of the following: WEEKS, DAYS, HOURS, MINUTES, SECONDS")
    @Enumerated(EnumType.STRING)
    private TimeUnit unit;
    private Double gradeToPass;
    @Min(value = 0, message = "allowed attempt cannot lower than 0")
    private Integer allowedAttempt;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Must be one of the following: HIGHEST, AVERAGE, LATEST")
    private GradingMethod gradingMethod;
    @NotNull(message = "shuffle must not be null")
    private Boolean shuffle;
    @NotNull(message = "reviewable must not be null")
    private Boolean reviewable;
    private String password;
    private List<String> tags;
    @NotNull(message = "max grade must not be null")
    private Double maxGrade;
    private UUID topicId;
    private UUID trainerId;
}
