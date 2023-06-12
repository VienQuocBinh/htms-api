package htms.repository;

import htms.model.ProgramPerClass;
import htms.model.embeddedkey.ProgramPerClassId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramPerCycleRepository extends JpaRepository<ProgramPerClass, ProgramPerClassId> {
}
