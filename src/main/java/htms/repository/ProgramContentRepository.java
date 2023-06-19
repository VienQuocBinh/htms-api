package htms.repository;

import htms.model.ProgramContent;
import htms.model.embeddedkey.ProgramContentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProgramContentRepository extends JpaRepository<ProgramContent, ProgramContentId> {

    List<ProgramContent> findById_Program_IdAndId_Trainer_Id(UUID programId, UUID trainerId);

}
