package htms.repository;

import htms.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    Optional<Topic> findTopicByNameAndProgramContent_Id_Program_IdAndProgramContent_Id_Trainer_Id(String name, UUID programId, UUID trainerId);
}
