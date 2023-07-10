package htms.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicRequest {
    private UUID id;
    @NotNull
    private String name;
    private String description;
    private UUID trainerId;
    private UUID programId;
    private List<TopicSlotRequest> slots;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TopicSlotRequest {
        private UUID id;
        private Integer slot;
    }

}
