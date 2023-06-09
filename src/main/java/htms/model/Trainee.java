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
public class Trainee extends BaseEntityAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String code;
    @NotNull
    private String phone;
    private Date birthdate;

    @OneToOne
    private Account account;
    @OneToOne
    private Profile profile;
    @OneToMany(mappedBy = "trainee", fetch = FetchType.LAZY)
    private List<Attendance> attendances;
    @OneToMany(mappedBy = "id.trainee", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;

    protected Trainee(TraineeBuilder<?, ?> builder) {
        super(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.code = builder.code;
        this.phone = builder.phone;
        this.birthdate = builder.birthdate;
        this.account = builder.account;
        this.profile = builder.profile;
        this.attendances = builder.attendances;
        this.enrollments = builder.enrollments;
    }
}
