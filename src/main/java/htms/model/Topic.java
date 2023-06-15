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
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "program_id", referencedColumnName = "program_id"),
            @JoinColumn(name = "trainer_id", referencedColumnName = "trainer_id")
    })
    private ProgramContent programContent;
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Activity> activities;
    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;
}
