package htms.model;

import htms.common.constance.TestType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer testNo;
    @NotNull
    private Date date;
    @NotNull
    private Long time;
    private String agenda;
    @Enumerated(EnumType.STRING)
    private TestType type;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "class_id", referencedColumnName = "class_id"),
            @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    })
    private ProgramPerClass programPerClass;
    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    private List<TestScore> testScore;
    @ManyToMany(mappedBy = "tests", fetch = FetchType.LAZY)
    private List<Question> questions;
}
