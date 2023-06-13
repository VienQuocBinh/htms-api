package htms.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramResponse {
    private UUID id;
    private String description;
    private String code;
    private DepartmentResponse department;
    @JsonProperty("programPerClassList")
    private List<ProgramPerClassResponse> programPerClasses;
}
