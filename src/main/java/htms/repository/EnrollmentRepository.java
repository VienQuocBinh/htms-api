package htms.repository;

import htms.common.constants.EnrollmentStatus;
import htms.model.Enrollment;
import htms.model.embeddedkey.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    @Query("""
            select e from Enrollment e
            where e.id.clazz.id = :classId
            and e.status = :status
            """)
    Optional<List<Enrollment>> findAllByClassIdAndStatus(
            @Param("classId") UUID classId,
            @Param("status") EnrollmentStatus status);
}
