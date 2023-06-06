package htms.model;

import htms.common.constance.ClassStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ClassStatus status;
    @Max(30)
    private int quantity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "cycle_id", referencedColumnName = "cycle_id"),
            @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    })
    private ProgramPerCycle programPerCycle;
}
