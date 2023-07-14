package htms.repository;

import htms.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    @Query("""
            SELECT a FROM Attendance a
            JOIN Schedule s ON s.id = a.schedule.id
            JOIN Class c ON c.id = s.clazz.id
            WHERE c.id = :classId
            """)
    Optional<List<Attendance>> findAllByClassId(@Param("classId") UUID classId);

    /**
     * Returns a list of trainee's attendances for the given OPENING class
     *
     * @param traineeId {@code traineeId}
     * @param classId   {@code classId}
     * @return a list of trainee's attendances {@code Optional<List<Attendance>>}
     */
    @Query("""
            select a from Attendance a
                        join Schedule s on s.id = a.schedule.id
                        join Class c on c.id = s.clazz.id
                        where c.id = :classId and a.trainee.id = :traineeId
                        and c.status = 'OPENING'
            """)
    Optional<List<Attendance>> findAllByTraineeIdAndClassId(@Param("traineeId") UUID traineeId,
                                                            @Param("classId") UUID classId);

    /**
     * Get the Attendance of the specified schedule of the trainee.
     *
     * @param scheduleId the schedule of the trainee
     * @param traineeId  the trainee
     * @return the Attendance of the specified schedule of the trainee
     */
    Optional<Attendance> findBySchedule_IdAndTrainee_Id(UUID scheduleId, UUID traineeId);
}
