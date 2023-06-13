package htms.repository;

import htms.model.ProgramPerClass;
import htms.model.embeddedkey.ProgramPerClassId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgramPerClassRepository extends JpaRepository<ProgramPerClass, ProgramPerClassId> {
    Optional<Long> countAllById_Program_Id(UUID programId);
}
