package htms.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionCategoryResponse {
    private UUID id;
    private String name;
    private String description;
    private String numberId;
    private Date craetedDate;
    private Date modifiedDate;
    private TrainerResponse trainer;
    private ProgramResponse programResponse;
    private ParentCategory parent;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static public class ParentCategory {
        private UUID id;
        private String name;
    }
}
