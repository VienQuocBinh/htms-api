package htms.repository;

import htms.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query(value = "SELECT * FROM Question q WHERE (string_to_array(q.tags, ',') && CAST(ARRAY[:tagList] AS TEXT[])) OR (:tagListLength = 0) AND q.category_id = :categoryId", nativeQuery = true)
    List<Question>  GetListOfQuestionByCategoryAndTags(@Param("tagList") List<String> tags, @Param("tagListLength") int tagsLength, @Param("categoryId") UUID categoryId);

    boolean existsByNumberIdAndQuestionCategory_Id(String numberId, UUID categoryId);
}
