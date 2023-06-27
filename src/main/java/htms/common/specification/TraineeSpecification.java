package htms.common.specification;

import htms.model.Account;
import htms.model.Department;
import htms.model.Trainee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class TraineeSpecification {
    /**
     * Filter by account title
     *
     * @param title {@link String}
     * @return {@link Specification<Trainee>}
     */
    public static Specification<Trainee> hasTitleEqual(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(accountJoin(root).get("title"), title);
    }

    /**
     * Filter by department id
     *
     * @param departmentId {@link UUID}
     * @return {@link Specification<Trainee>}
     */
    public static Specification<Trainee> hasDepartmentEqual(UUID departmentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(departmentJoin(root).get("id"), departmentId);
    }

    /**
     * Search for a trainee with the given name
     *
     * @param name {@link String}
     * @return {@link Specification<Trainee>}
     */
    public static Specification<Trainee> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%");
    }

    /**
     * Search for a trainee with the given code
     *
     * @param code {@link String}
     * @return {@link Specification<Trainee>}
     */
    public static Specification<Trainee> hasCodeLike(String code) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("code")),
                        "%" + code.toLowerCase() + "%");
    }

    private static Join<Trainee, Account> accountJoin(Root<Trainee> root) {
        return root.join("account");
    }

    private static Join<Trainee, Department> departmentJoin(Root<Trainee> root) {
        return root.join("account").join("department");
    }
}
