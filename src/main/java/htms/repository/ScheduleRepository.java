package htms.repository;

import htms.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Optional<List<Schedule>> findAllByClazz_Id(UUID classId);

    Optional<List<Schedule>> findAllByTrainer_Id(UUID trainerId);
}
