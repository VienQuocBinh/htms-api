package htms.api.domain;

import htms.common.constants.FilterOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
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

    // Add CriteriaBuilder and Path fields (optional)
    private transient CriteriaBuilder builder;
    private transient Path<?> path;
    private transient Class<?> valueType; // Added field for type information

    public FilterCondition(String field, FilterOperation filterOperation, String order) {
        this.field = field;
        this.operator = filterOperation;
        this.value = order;
    }
}
