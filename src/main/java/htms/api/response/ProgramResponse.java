package htms.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramResponse {
    private UUID id;
    private String name;
    private String description;
    private String code;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Boolean isActive;
    private DepartmentResponse department;
}
