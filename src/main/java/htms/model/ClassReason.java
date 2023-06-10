package htms.model;

import htms.common.constance.ClassReasonType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @Enumerated(EnumType.STRING)
    private ClassReasonType type;

    @OneToMany(mappedBy = "id.classReason")
    private List<ClassReasonDetail> classReasonDetails;
}
