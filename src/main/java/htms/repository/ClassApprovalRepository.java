package htms.repository;

import htms.model.ClassApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassApprovalRepository extends JpaRepository<ClassApproval, Long> {
}
