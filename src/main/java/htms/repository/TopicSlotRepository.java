package htms.repository;

import htms.model.ExternalResource;
import htms.model.TopicSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopicSlotRepository extends JpaRepository<TopicSlot, UUID> {

    Optional<TopicSlot> findByExternalResource_Id(UUID externalResourceId);
    Optional<TopicSlot> findByQuiz_Id(UUID quizId);
}
