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
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @Column(length = 1000)
    private String description;
    private Integer duration; // months
    @OneToMany(mappedBy = "cycle", fetch = FetchType.LAZY)
    private List<Class> classes;
}
