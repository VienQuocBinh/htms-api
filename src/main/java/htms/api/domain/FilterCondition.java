package htms.api.domain;

import htms.common.constants.FilterOperation;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FilterCondition {
    private String field;
    private FilterOperation operator;
    private Object value;
}
