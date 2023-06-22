package htms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ActivityDetail {
    private UUID id;
    private String name;
    private String description;
    private String materialLink;
    private List<AssignmentThumbnailInfo> assignments;
    private TestThumbnailInfo test;
}
