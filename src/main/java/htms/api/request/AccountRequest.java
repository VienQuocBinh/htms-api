package htms.api.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @Email
    private String email;
    private String title;
    private Long roleId;
}
