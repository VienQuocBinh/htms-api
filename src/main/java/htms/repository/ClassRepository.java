package htms.repository;

import htms.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<Class, UUID> {
    List<Class> findAllByOrderByCreatedDateAsc();
    List<Class> findAllByOrderByCreatedDateDesc();

    @Query("""
    select c from
    Trainee t join Enrollment e on t.id  = e.id.trainee.id\s
    		  join Class c on c.id = e.id.clazz.id
    where e.id.trainee.id = :traineeId and e.status = 'APPROVE'
""")
    Optional<List<Class>> findAllCurrentTakingClassesByTrainee(@Param("traineeId") UUID traineeId);
}
