package htms.repository;

import htms.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<Class, UUID> {
    List<Class> findAllByOrderByCreatedDateAsc();

    List<Class> findAllByOrderByCreatedDateDesc();
}
