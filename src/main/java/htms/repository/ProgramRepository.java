package htms.repository;

import htms.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<Program, UUID> {

    @Query(value = """
    select p from
    Trainee t inner join Enrollment e on t.id  = e.id.trainee.id
    		  inner join Class c on c.id = e.id.clazz.id
    		  inner join Program p on p.id = c.program.id
    where e.id.trainee.id = :traineeId
    and e.status = 'APPROVE'
""")
    Optional<List<Program>> getCurrentTakingClassesOfATrainee(@Param("traineeId") UUID traineeId);

}
