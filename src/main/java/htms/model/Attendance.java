package htms.model;

import htms.common.constants.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends BaseEntityAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    protected Attendance(AttendanceBuilder<?, ?> builder) {
        super(builder);
        this.id = builder.id;
        this.trainee = builder.trainee;
        this.schedule = builder.schedule;
        this.status = builder.status;
    }
}
