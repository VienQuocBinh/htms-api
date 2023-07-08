package htms.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class QuestionCategoryRequest {
    private UUID id;
    private String name;
    private String numberId;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private UUID trainerId;
    private UUID programId;
    private UUID parent;
}
