package htms.service.impl;

import htms.api.domain.FilterCondition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class is used to build all the queries passed as parameters.
 * filterAndConditions (filter list for the AND operator)
 * filterOrConditions (filter list for the OR operator)
 */
public class GenericFilterCriteriaBuilder<T> {
    private static final Map<String, Function<FilterCondition, Predicate>>
            FILTER_PREDICATES = new HashMap<>();

    // Create map of filter predicates
    static {
        FILTER_PREDICATES.put("EQUAL", condition -> condition.getBuilder().equal(condition.getPath(), condition.getValue()));
        FILTER_PREDICATES.put("NOT_EQUAL", condition -> condition.getBuilder().notEqual(condition.getPath(), condition.getValue()));
//        FILTER_PREDICATES.put("GREATER_THAN", condition -> condition.getBuilder().greaterThan(condition.getPath(), (Comparable) condition.getValue()));
//        FILTER_PREDICATES.put("GREATER_THAN_OR_EQUAL_TO", condition -> condition.getBuilder().greaterThanOrEqualTo(condition.getPath(), (Comparable) condition.getValue()));
//        FILTER_PREDICATES.put("LESS_THAN", condition -> condition.getBuilder().lessThan(condition.getPath(), (Comparable) condition.getValue()));
//        FILTER_PREDICATES.put("LESS_THAN_OR_EQUAL_TO", condition -> condition.getBuilder().lessThanOrEqualTo(condition.getPath(), (Comparable) condition.getValue()));
//        FILTER_PREDICATES.put("CONTAINS", condition -> condition.getBuilder().like(condition.getPath(), "%" + condition.getValue() + "%"));
        FILTER_PREDICATES.put("JOIN", condition -> condition.getBuilder().equal(condition.getPath(), condition.getValue()));
    }

    private final List<FilterCondition> filterAndConditions;
    private final List<FilterCondition> filterOrConditions;

    public GenericFilterCriteriaBuilder() {
        filterOrConditions = new ArrayList<>();
        filterAndConditions = new ArrayList<>();
    }

    public Specification<T> buildSpecification(List<FilterCondition> andConditions, List<FilterCondition> orConditions) {
        if (andConditions != null && !andConditions.isEmpty()) {
            filterAndConditions.addAll(andConditions);
        }
        if (orConditions != null && !orConditions.isEmpty()) {
            filterOrConditions.addAll(orConditions);
        }

        return (root, query, builder) -> {
            List<Predicate> predicatesAndClause = buildPredicates(filterAndConditions, root, builder);
            List<Predicate> predicatesOrClause = buildPredicates(filterOrConditions, root, builder);

            if (!predicatesAndClause.isEmpty() && !predicatesOrClause.isEmpty()) {
                Predicate andPredicate = builder.and(predicatesAndClause.toArray(new Predicate[0]));
                Predicate orPredicate = builder.or(predicatesOrClause.toArray(new Predicate[0]));
                return builder.and(andPredicate, orPredicate);
            } else if (!predicatesAndClause.isEmpty()) {
                return builder.and(predicatesAndClause.toArray(new Predicate[0]));
            } else if (!predicatesOrClause.isEmpty()) {
                return builder.or(predicatesOrClause.toArray(new Predicate[0]));
            } else {
                return null;
            }
        };
    }

    private List<Predicate> buildPredicates(List<FilterCondition> conditions, Root<T> root, CriteriaBuilder builder) {
        return conditions.stream()
                .map(condition -> buildPredicate(condition, root, builder))
                .collect(Collectors.toList());
    }

    private Predicate buildPredicate(FilterCondition condition, Root<T> root, CriteriaBuilder builder) {
        Function<FilterCondition, Predicate> predicateFunction = FILTER_PREDICATES.get(condition.getOperator().name());

        if (predicateFunction == null) {
            throw new IllegalArgumentException("Invalid predicate function: " + condition.getOperator().name());
        }

        Path<?> path = getPath(root, condition.getField());
        condition.setBuilder(builder);
        condition.setPath(path);
        return predicateFunction.apply(condition);
    }

    private Path<?> getPath(Root<T> root, String field) {
        String[] fieldParts = field.split("\\.");
        Path<?> path = root.get(fieldParts[0]);
        for (int i = 1; i < fieldParts.length; i++) {
            path = path.get(fieldParts[i]);
        }
        return path;
    }
}
