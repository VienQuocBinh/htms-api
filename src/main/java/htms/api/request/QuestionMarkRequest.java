package htms.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionMarkRequest {
    private UUID id;
    @NotNull(message = "mark must not be null")
    @Min(value = 1, message = "mark must greater than 0")
    private Double mark;
}
