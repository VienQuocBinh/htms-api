package htms.model.embeddedkey;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class ClassSubjectId implements Serializable {
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID classSubjectId;
}
