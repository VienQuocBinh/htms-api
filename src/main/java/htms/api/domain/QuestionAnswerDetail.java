package htms.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude
public class QuestionAnswerDetail {
    private UUID id;
    private String answerText;
    private Double fraction;
    private String feedback;
    private int position;
}
