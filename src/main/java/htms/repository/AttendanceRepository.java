package htms.repository;

import htms.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    @Query("""
            SELECT a FROM Attendance a
            JOIN Schedule s ON s.id = a.schedule.id
            JOIN Class c ON c.id = s.clazz.id
            WHERE c.id = :classId
            """)
    Optional<List<Attendance>> findAllByClassId(@Param("classId") UUID classId);

    Optional<List<Attendance>> findAllByTrainee_Id(UUID traineeId);
}
