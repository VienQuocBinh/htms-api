package htms.repository;

import htms.model.ClassApproval;
import htms.model.GroupedApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassApprovalRepository extends JpaRepository<ClassApproval, Long>, JpaSpecificationExecutor<ClassApproval> {

    @Query("select new htms.model.GroupedApprovalStatus(ca.clazz.id, max(ca.createdDate)) from ClassApproval ca group by ca.clazz.id")
    List<GroupedApprovalStatus> getLatestClassApprovalsGroupedByClazzId();

    @Query("select ca from ClassApproval ca where ca.clazz.id = :id order by ca.createdDate desc LIMIT 1")
    Optional<ClassApproval> getClassApprovalByClazzIdOrderByCreatedDateDesc(@Param("id") UUID id);

    @Query("SELECT MAX(c.id) FROM ClassApproval c")
    Optional<Long> getLatestId();
}
