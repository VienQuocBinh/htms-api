package htms.api.request;

import htms.api.domain.QuestionAnswerDetail;
import htms.common.constants.QuestionStatus;
import htms.common.constants.QuestionType;
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
public class QuestionRequest {
    private UUID id;
    private String numberId;
    private QuestionType type;
    private boolean shuffle;
    private String name;
    private QuestionStatus status;
    private String questionText;
    private Double defaultMark;
    private String generalFeedback;
    private UUID trainerId;
    private List<String> tags;
    private UUID categoryId;
    private List<QuestionAnswerDetail> answers;
}
