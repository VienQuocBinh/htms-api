package htms.model.embeddedkey;

import htms.model.Class;
import htms.model.Program;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class ProgramPerClassId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
    //    @ManyToOne
//    @JoinColumn(name = "cycle_id")
//    private Cycle cycle;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz;
}
