package htms.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerRequest {
    @NotNull
    private String name;
    @NotNull
    @Length(min = 9, max = 12)
    private String phone;
    @NotNull
    private Date birthdate;
    @NotNull
    private UUID accountId;
}
