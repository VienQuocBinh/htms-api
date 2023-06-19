package htms.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import htms.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicDetail {
    private UUID id;
    private String name;
    private String description;
    private List<ActivityDetail> activities;
}
