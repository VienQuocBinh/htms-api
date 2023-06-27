package htms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Class extends BaseEntityAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String code;
    private String generalSchedule; // Start{10:00,MON};Stop{11:00,MON};Start{17:00,MON};Stop{19:00,MON}
    private Integer quantity; // real quantity
    private Integer minQuantity;
    private Integer maxQuantity;
    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY)
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY)
    private List<ClassApproval> classApprovals;
    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;
    @OneToMany(mappedBy = "id.clazz", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    public Class(ClassBuilder<?, ?> builder) {
        super(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.code = builder.code;
        this.quantity = builder.quantity;
        this.minQuantity = builder.minQuantity;
        this.maxQuantity = builder.maxQuantity;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.generalSchedule = builder.generalSchedule;
        this.schedules = builder.schedules;
        this.classApprovals = builder.classApprovals;
        this.cycle = builder.cycle;
        this.enrollments = builder.enrollments;
        this.trainer = builder.trainer;
        this.program = builder.program;
    }
}
