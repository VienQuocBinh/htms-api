package htms.model.embeddedkey;

import htms.model.ProgramPerCycle;
import htms.model.Trainee;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class EnrollmentId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "program_id", referencedColumnName = "program_id"),
            @JoinColumn(name = "cycle_id", referencedColumnName = "cycle_id")
    })
    private ProgramPerCycle programPerCycle;
}
