package htms.service.impl;

import htms.api.domain.FilterCondition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class is used to build all the queries passed as parameters.
 * filterAndConditions (filter list for the AND operator)
 * filterOrConditions (filter list for the OR operator)
 */
public class GenericFilterCriteriaBuilder<T> {
    private final List<FilterCondition> filterAndConditions;
    private final List<FilterCondition> filterOrConditions;

    public GenericFilterCriteriaBuilder() {
        filterAndConditions = new ArrayList<>();
        filterOrConditions = new ArrayList<>();
    }

    public Specification<T> addCondition(List<FilterCondition> andConditions, List<FilterCondition> orConditions) {
        if (andConditions != null && !andConditions.isEmpty()) {
            filterAndConditions.addAll(andConditions);
        }
        if (orConditions != null && !orConditions.isEmpty()) {
            filterOrConditions.addAll(orConditions);
        }

        Map<String, Function<FilterCondition, Specification<T>>> filterSpecifications = createFilterSpecifications();

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            applySpecifications(filterAndConditions, filterSpecifications, root, query, criteriaBuilder, predicates);

            if (!filterOrConditions.isEmpty()) {
                List<Predicate> orPredicates = new ArrayList<>();
                applySpecifications(filterOrConditions, filterSpecifications, root, query, criteriaBuilder, orPredicates);
                predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void applySpecifications(List<FilterCondition> conditions, Map<String, Function<FilterCondition, Specification<T>>> filterSpecifications,
                                     Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        for (FilterCondition condition : conditions) {
            Function<FilterCondition, Specification<T>> specificationFunction = filterSpecifications.get(condition.getOperator().name());

            if (specificationFunction != null) {
                Specification<T> specification = specificationFunction.apply(condition);
                predicates.add(specification.toPredicate(root, query, criteriaBuilder));
            }
        }
    }

    private Map<String, Function<FilterCondition, Specification<T>>> createFilterSpecifications() {
        Map<String, Function<FilterCondition, Specification<T>>> filterSpecifications = new HashMap<>();

        filterSpecifications.put("EQUAL", condition -> (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(condition.getField()), condition.getValue()));
        filterSpecifications.put("NOT_EQUAL", condition -> (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get(condition.getField()), condition.getValue()));
        filterSpecifications.put("GREATER_THAN", condition -> (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(condition.getField()), String.valueOf(condition.getValue())));
        filterSpecifications.put("GREATER_THAN_OR_EQUAL_TO", condition -> (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(condition.getField()), String.valueOf(condition.getValue())));
        filterSpecifications.put("LESS_THAN", condition -> (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(condition.getField()), String.valueOf(condition.getValue())));
        filterSpecifications.put("LESS_THAN_OR_EQUAL_TO", condition -> (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(condition.getField()), String.valueOf(condition.getValue())));
        filterSpecifications.put("CONTAINS", condition -> (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(condition.getField())), "%" + String.valueOf(condition.getValue()).toLowerCase() + "%"));
        filterSpecifications.put("JOIN", condition -> (root, query, criteriaBuilder) ->
        {
            List<String> joinClause = Stream.of(condition.getField().split("\\.")).toList();
            return criteriaBuilder.equal(root.join(joinClause.get(0)).get(joinClause.get(1)), condition.getValue());
        });
        return filterSpecifications;
    }
}
