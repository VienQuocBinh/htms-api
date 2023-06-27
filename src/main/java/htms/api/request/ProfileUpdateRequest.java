package htms.api.request;

import htms.common.constants.ProfileStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
}
