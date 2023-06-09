package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Schedule extends BaseEntityAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private Date date;
    @NotNull
    private Date startTime;
    @NotNull
    private Date endTime;

    @OneToOne
    private Room room;
    @OneToMany(mappedBy = "schedule")
    private List<Attendance> attendance;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz;
    @OneToOne
    private Trainer trainer;
    @OneToOne
    private ChangedSchedule changedSchedule;

    public Schedule(ScheduleBuilder<?, ?> builder) {
        super(builder);
        this.id = builder.id;
        this.date = builder.date;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.room = builder.room;
        this.attendance = builder.attendance;
        this.clazz = builder.clazz;
        this.trainer = builder.trainer;
        this.changedSchedule = builder.changedSchedule;
    }
}
