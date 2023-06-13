package htms.repository;

import htms.model.ClassApproval;
import htms.model.GroupedApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassApprovalRepository extends JpaRepository<ClassApproval, Long> {

    @Query("select new htms.model.GroupedApprovalStatus(ca.clazz.id, max(ca.createdDate)) from ClassApproval ca group by ca.clazz.id")
    List<GroupedApprovalStatus> getLatestClassApprovalsGroupedByClazzId();
}
