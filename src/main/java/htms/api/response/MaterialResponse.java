package htms.api.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialResponse {
    private UUID id;
    private String name;
    private String link;
    private String description;
}
