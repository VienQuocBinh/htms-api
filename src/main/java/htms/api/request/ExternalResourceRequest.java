package htms.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalResourceRequest {
    private UUID id;
    private String name;
    private String description;
    private String externalUrl;
    private UUID topicId;
}
