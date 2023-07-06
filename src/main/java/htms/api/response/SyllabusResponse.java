package htms.api.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusResponse {
    private UUID id;
    private String name;
    private String description;
    private String passCriteria;
    private Short totalSessions;
    private List<AssessmentSchemeResponse> assessmentSchemes;
    private List<MaterialResponse> materials;
    private List<ModuleResponse> modules;

}
