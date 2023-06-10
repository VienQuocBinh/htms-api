package htms.model.embeddedkey;

import htms.model.Class;
import htms.model.ClassReason;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ClassReasonId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "class_reason_id")
    private ClassReason classReason;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz;
}
