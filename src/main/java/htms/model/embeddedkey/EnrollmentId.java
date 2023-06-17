package htms.model.embeddedkey;

import htms.model.Class;
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
    //    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "program_id", referencedColumnName = "program_id"),
//            @JoinColumn(name = "class_id", referencedColumnName = "class_id")
//    })
//    private ProgramPerClass programPerClass;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz;
}
