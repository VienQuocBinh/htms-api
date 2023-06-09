package htms.repository;

import htms.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, UUID> {
    /**
     * Get all trainees in a given class base on the enrollment which is not cancelled
     *
     * @param classId
     * @return
     */
//    @Query("""
//            select t from Trainee t\s
//            join Enrollment e on e.id.trainee.id = t.id\s
//            join ProgramPerCycle ppc on e.id.cycle.id = ppc.id.cycle.id\s
//            and e.id.program.id = ppc.id.program.id\s
//            join Class c on c.programPerCycle.id.cycle.id = ppc.id.cycle.id\s
//            and c.programPerCycle.id.program.id = ppc.id.program.id\s
//            where c.id = :class_id and e.isCancelled = false""")
//    Optional<List<Trainee>> findAllByClassId(@Param("class_id") UUID classId);

}
