package htms.common.specification;

import htms.common.constants.ClassApprovalStatus;
import htms.model.Class;
import htms.model.ClassApproval;
import htms.service.impl.GenericFilterCriteriaBuilder;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ClassSpecification extends GenericFilterCriteriaBuilder<Class> {

    public static Specification<Class> classesWithLatestApproval(ClassApprovalStatus status) {

        return (root, query, criteriaBuilder) -> {

            // Create the subquery to fetch the latest approval date for each class
            Subquery<Date> subquery = query.subquery(Date.class);
            Root<ClassApproval> subqueryRoot = subquery.from(ClassApproval.class);
            Join<ClassApproval, Class> classJoin = subqueryRoot.join("clazz");

            Expression<Date> createdDateExpression = subqueryRoot.get("createdDate");
            subquery.select(criteriaBuilder.greatest(createdDateExpression))
                    .groupBy(subqueryRoot.get("clazz").get("id"), classJoin.get("id"))
                    .having(criteriaBuilder.equal(classJoin, root));

            // Join the Class entity with ClassApproval and apply the subquery to retrieve the latest approval records
            Join<Class, ClassApproval> approvalJoin = root.join("classApprovals");
            Predicate latestApprovalPredicate = criteriaBuilder.equal(approvalJoin.get("createdDate"), subquery);
            query.where(
                    criteriaBuilder.and(latestApprovalPredicate,
                            criteriaBuilder.equal(approvalJoin.get("status"), status)));
            return query.getRestriction();
        };
    }

}
