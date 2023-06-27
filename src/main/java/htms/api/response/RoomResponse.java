package htms.api.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private UUID id;
    private String name;
    private Integer maxCapacity;
}
