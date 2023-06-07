package htms.model.embeddedkey;

import htms.model.Cycle;
import htms.model.Program;
import htms.model.Trainee;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "program_id")
    private Program program;
    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;
}
