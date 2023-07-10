package htms.repository;

import htms.model.ExternalResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExternalResourceRepository extends JpaRepository<ExternalResource, UUID> {
}
