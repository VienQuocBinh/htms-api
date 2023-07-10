package htms.repository;

import htms.model.Program;
import htms.model.QuestionCategory;
import htms.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, UUID> {
    Optional<List<QuestionCategory>> findAllByTrainerAndProgram(Trainer trainer, Program program);

    boolean existsByNumberIdAndProgram_Id(String numberId, UUID programId);

}
