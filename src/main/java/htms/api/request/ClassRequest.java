package htms.api.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {
    private String name;
    private Short minQuantity;
    private Short maxQuantity;
}
