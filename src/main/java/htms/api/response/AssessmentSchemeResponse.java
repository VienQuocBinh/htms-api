package htms.api.response;

import htms.common.constants.AssessmentSchemeCategory;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentSchemeResponse {
    private Integer id;
    private AssessmentSchemeCategory category;
    private Double weight;
}
