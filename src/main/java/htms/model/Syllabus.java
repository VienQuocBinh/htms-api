package htms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Syllabus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private String passCriteria;
    private Short totalSessions;

    @OneToMany(mappedBy = "syllabus", fetch = FetchType.LAZY)
    private List<AssessmentScheme> assessmentSchemes;
    @OneToMany(mappedBy = "syllabus", fetch = FetchType.LAZY)
    private List<Material> materials;
    @OneToMany(mappedBy = "syllabus", fetch = FetchType.LAZY)
    private List<Module> modules;
}
