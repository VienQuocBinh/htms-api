package htms.model;

import htms.common.constance.ClassStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
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
    private Integer minQuantity;
    private Integer maxQuantity;
    private Date startDate;
    private Date endDate;
    private String code;
    private String reason;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "cycle_id", referencedColumnName = "cycle_id"),
            @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    })
    private ProgramPerCycle programPerCycle;

    @OneToMany(mappedBy = "clazz")
    private List<Schedule> schedules;
}
