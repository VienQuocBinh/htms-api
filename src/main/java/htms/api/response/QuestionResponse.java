package htms.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import htms.api.domain.QuestionAnswerDetail;
import htms.common.constants.QuestionStatus;
import htms.common.constants.QuestionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponse {
    private UUID id;
    private String numberId;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    private boolean shuffle;
    private String name;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
    private String questionText;
    private Double defaultMark;
    private String generalFeedback;
    private TrainerInfo trainer;
    private List<String> tags;
    private CategoryInfo category;
    private List<QuestionAnswerDetail> answers;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TrainerInfo {
        private UUID id;
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CategoryInfo {
        private UUID id;
        private String name;
    }
}
