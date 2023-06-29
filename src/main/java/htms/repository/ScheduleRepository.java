package htms.repository;

import htms.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Optional<List<Schedule>> findAllByClazz_Id(UUID classId);

    Optional<List<Schedule>> findAllByTrainer_Id(UUID trainerId);

    @Query("""
            select s from Schedule s
            join Attendance  a on a.schedule.id = s.id
            join Trainee t on t.id = a.trainee.id
            where t.id = :traineeId
            """)
    Optional<List<Schedule>> findAllByTraineeId(@Param("traineeId") UUID traineeId);
}
