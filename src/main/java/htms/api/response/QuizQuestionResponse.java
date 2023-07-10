package htms.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import htms.common.constants.QuestionStatus;
import htms.common.constants.QuestionType;
import htms.model.QuestionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizQuestionResponse {
    private UUID id;
    private String numberId;
    private QuestionType type;
    private boolean shuffle;
    private String name;
    private QuestionStatus status;
    private String questionText;
    private Double defaultMark;
    private String generalFeedBack;
    private Date createdDate;
    private Date modifiedDate;
    private String tags;
    private QuestionCategory questionCategory;
    private List<QuizQuestionAnswerResponse> answers;
    private Integer slot; //keep order of the question in the question list;
    // fields for random question
    private boolean isRandomQuestion;
    private QuestionCategory targetCategory;
    private String targetTags;
}
