package htms.model;

import htms.model.embeddedkey.ClassReasonId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassReasonDetail {
    @EmbeddedId
    private ClassReasonId id;
    private String other;
}
