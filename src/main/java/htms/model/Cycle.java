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
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 1000)
    private String description;
    private Integer duration; // months

    @OneToMany(mappedBy = "cycle", fetch = FetchType.LAZY)
    private List<ProgramPerClass> programPerClasses;
}
