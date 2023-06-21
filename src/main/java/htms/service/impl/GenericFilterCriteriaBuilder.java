package htms.service.impl;

import htms.api.domain.FilterCondition;
import htms.util.FieldCastingUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
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
        FieldCastingUtil fieldCastingUtil = new FieldCastingUtil();
        filterSpecifications.put("EQUAL", condition -> (root, query, criteriaBuilder) -> {
            var value = condition.getValue();
            var conditionField = condition.getField();
            // If the field type is Date then build the criteria for Date
            if (conditionField.toUpperCase().contains("DATE")) {
                // Cast string value to LocalDate value
                value = fieldCastingUtil.castStringToFieldType(String.valueOf(value), Date.class);
                // compare only date part
                return criteriaBuilder.equal(
                        criteriaBuilder.function("date_trunc", LocalDate.class, criteriaBuilder.literal("day"), root.get(conditionField)),
                        value);
            }
            // Cast the value to appropriate type
            Field[] fields = root.getModel().getJavaType().getDeclaredFields(); // get list of root class fields
            // Cast string value to the type of root field has the same name
            for (Field field : fields) {
                if (field.getName().equals(conditionField)) {
                    var type = field.getType();  // Get the field data type
                    value = fieldCastingUtil.castStringToFieldType(String.valueOf(value), type);
                    break;
                }
            }
            return criteriaBuilder.equal(root.get(condition.getField()), value);
        });
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
            // get the root data type
            var rootModel = root.getModel().getJavaType();
            Field childField;
            try {
                childField = rootModel.getDeclaredField(joinClause.get(0));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

            Field[] childFields = childField.getType().getDeclaredFields(); // get list of child fields
            var value = condition.getValue();
            // Cast string value to the type of root field has the same name
            for (Field field : childFields) {
                if (field.getName().equals(joinClause.get(1))) {
                    // Get the field data type
                    var type = field.getType();
                    // Cast string value to appropriate data type
                    value = fieldCastingUtil.castStringToFieldType(String.valueOf(condition.getValue()), type);
                    break;
                }
            }
            return criteriaBuilder.equal(root.join(joinClause.get(0)).get(joinClause.get(1)), value);
        });
        return filterSpecifications;
    }
}
