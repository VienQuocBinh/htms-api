package htms.api.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponse {
    private UUID id;
    private String description;
    private Integer moduleNo;
    private String link;
    // Module properties
    private List<SessionResponse> sessions;
}
