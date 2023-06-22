package htms.repository;

import htms.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, UUID>, JpaSpecificationExecutor<Trainee> {
    /**
     * Get all trainees in a given class base on the enrollment which is not cancelled
     *
     * @param classId {@link UUID}
     * @return {@code Optional<List<Trainee>>}
     */
    @Query(value = """
            select t from  Trainee t\s
            join Enrollment e ON t.id  = e.id.trainee.id\s
            join Class c on c.id = e.id.clazz.id
            where c.id = :classId\s
            and e.isCancelled = false
            order by e.enrollmentDate asc\s
            """)
    Optional<List<Trainee>> findAllByClassId(@Param("classId") UUID classId);
}
