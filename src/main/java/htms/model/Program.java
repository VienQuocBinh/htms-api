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
    @Column(length = 1000)
    private String description;
    @NotNull
    @Column(unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "id.program")
    private List<ProgramPerClass> programPerClasses;
    @OneToOne
    private Syllabus syllabus;
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<QuestionBank> questionBanks;
}
