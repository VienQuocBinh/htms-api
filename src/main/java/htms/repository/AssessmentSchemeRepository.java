package htms.repository;

import htms.model.AssessmentScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentSchemeRepository extends JpaRepository<AssessmentScheme, Integer> {
    
}
