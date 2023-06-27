package htms.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramContentRequest {

    @NotNull(message = "programId is required")
    private UUID programId;
    @NotNull(message = "trainerId is required")
    private UUID trainerId;
    private List<TopicRequest> topics;
}
