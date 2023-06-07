package htms.model;

import htms.common.constance.AssessmentSchemeCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private AssessmentSchemeCategory category;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
}
