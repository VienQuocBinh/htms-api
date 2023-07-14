package htms.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ApprovalRequest {
    @NotNull(message = "classId is required")
    @JsonProperty("classId")
    private UUID id;
    @NotNull(message = "comment is required")
    private String comment;
    private Date dueDate; // Deadline for making approval of the class
}
