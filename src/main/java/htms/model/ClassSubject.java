package htms.model;

import htms.common.constance.ClassStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubject {
    //    @EmbeddedId
//    private ClassSubjectId id;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID classSubjectId;
    private String title;
    @Enumerated(EnumType.STRING)
    private ClassStatus status;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "cycle_id", referencedColumnName = "cycle_id"),
            @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    })
    private ProgramPerCycle programPerCycle;
}
