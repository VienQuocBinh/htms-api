package htms.repository;

import htms.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID> {

    @Query(value = """
                select p from
                Trainee t inner join Enrollment e on t.id  = e.id.trainee.id
                		  inner join Class c on c.id = e.id.clazz.id and c.status = 'OPENING'
                		  inner join Program p on p.id = c.program.id
                where e.id.trainee.id = :traineeId
                and e.status = 'APPROVE' and e.isCancelled = false
            """)
    Optional<List<Program>> findAllCurrentTakingProgramsByTraineeId(@Param("traineeId") UUID traineeId);

    @Query("""
            select p from Program p
            join Class c on p.id = c.program.id and c.status = 'OPENING' and c.isDeleted = false
            join Trainer t on c.trainer.id = t.id
            where t.id = :trainerId
            """)
    Optional<List<Program>> findAllCurrentTeachingProgramsByTrainerId(@Param("trainerId") UUID trainerId);
}
