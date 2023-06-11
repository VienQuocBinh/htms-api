package htms.repository;

import htms.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, UUID> {
    /**
     * Get all trainees in a given class base on the enrollment which is not cancelled
     *
     * @param classId {@link UUID}
     * @return {@code Optional<List<Trainee>>}
     */
    @Query(value = """
            select t from Trainee t\s
            join Enrollment e on e.id.trainee.id = t.id\s
            join ProgramPerClass ppc on e.id.programPerClass.id.program.id = ppc.id.program.id
             and e.id.programPerClass.id.clazz.id = ppc.id.clazz.id\s
            join Class c on c.id = ppc.id.clazz.id\s
            where ppc.id.clazz.id = :classId\s
            and ppc.id.program.id = :programId\s
            and e.isCancelled = false
            order by e.enrollmentDate asc
            """)
    Optional<List<Trainee>> findAllByClassAndProgram(@Param("classId") UUID classId,
                                                     @Param("programId") UUID programId);
//
}
