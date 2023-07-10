package htms.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer duration;
    private Integer unitNo;
}
