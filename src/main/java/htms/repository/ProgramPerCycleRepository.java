package htms.repository;

import htms.model.ProgramPerCycle;
import htms.model.embeddedkey.ProgramPerCycleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramPerCycleRepository extends JpaRepository<ProgramPerCycle, ProgramPerCycleId> {
}
