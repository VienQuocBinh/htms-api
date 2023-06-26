package htms.api.request;

import htms.common.constants.ProfileStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    private ProfileStatus status;
}
