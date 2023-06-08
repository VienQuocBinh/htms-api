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
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer sessionNo;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
    private List<Unit> units;
}
