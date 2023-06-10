package htms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String file;
    private String description;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "program_id", referencedColumnName = "program_id"),
            @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    })
    private ProgramPerClass programPerClass;
}
