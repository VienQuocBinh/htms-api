package htms.repository;

import htms.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, UUID> {
}
