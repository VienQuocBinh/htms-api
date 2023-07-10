package htms.repository;

import htms.common.constants.ClassStatus;
import htms.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<Class, UUID>, JpaSpecificationExecutor<Class> {
    List<Class> findAllByOrderByCreatedDateAsc();

    List<Class> findAllByOrderByCreatedDateDesc();

    @Query("""
                select c from
                Trainee t join Enrollment e on t.id  = e.id.trainee.id\s
                		  join Class c on c.id = e.id.clazz.id
                where e.id.trainee.id = :traineeId
                and (e.status = 'APPROVE' or e.status = 'PENDING')
                and (c.status = 'OPENING' or c.status = 'PENDING' or c.status = 'PLANNING')
            """)
    Optional<List<Class>> findAllCurrentTakingClassesByTrainee(@Param("traineeId") UUID traineeId);

    Optional<List<Class>> findAllByTrainer_Id(UUID trainerId);

    Optional<List<Class>> findAllByProgram_IdAndStatusEquals(UUID programId, ClassStatus status);

    Optional<List<Class>> findAllByCodeStartsWith(String codeStartsWith);
}
