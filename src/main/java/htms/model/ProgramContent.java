package htms.model;

import htms.model.embeddedkey.ProgramContentId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramContent {
    @EmbeddedId
    private ProgramContentId id;

    @OneToMany(mappedBy = "programContent", fetch = FetchType.LAZY)
    private List<Topic> topics;
}
