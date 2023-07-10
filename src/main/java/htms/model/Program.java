package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @Column(length = 1000)
    private String description;
    @NotNull
    @Column(unique = true)
    private String code;
    @Builder.Default
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToOne
    private Syllabus syllabus;
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<QuestionCategory> questionBanks;
    @OneToMany(mappedBy = "id.program")
    private List<ProgramContent> programContents;
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<Class> classes;
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<QuestionCategory> questionCategories;
}
