package htms.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityRequest {
    @NotNull(message = "topicName is required")
    private String topicName;
    @NotNull(message = "programId is required")
    private UUID programId;
    @NotNull(message = "trainerId is required")
    private UUID trainerId;
    private String description;
    private String materialLink;
    @NotNull(message = "activity name is required")
    private String name;
}
