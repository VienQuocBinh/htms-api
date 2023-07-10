package htms.api.request;

import jakarta.validation.constraints.NotNull;
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
public class QuizQuestionRequest {
    private UUID quizId;
    private Integer quantity;
    @NotNull(message = "isRandom cannot be null")
    private Boolean isRandom;
    private List<String> tags;
    private UUID categoryId;
    private List<UUID> selected;
    @Builder.Default
    private Boolean isHidden = false;
}
