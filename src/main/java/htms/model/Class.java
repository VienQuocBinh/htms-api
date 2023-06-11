package htms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
    private String code;
    private String generalSchedule; // Start{10:00,MON};Stop{11:00,MON};Start{17:00,MON};Stop{19:00,MON}

    @OneToMany(mappedBy = "clazz")
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "clazz")
    private List<ClassApproval> classApprovals;
    @OneToMany(mappedBy = "clazz")
    private List<AdditionalMaterial> additionalMaterials;

    public Class(ClassBuilder<?, ?> builder) {
        super(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.code = builder.code;
        this.generalSchedule = builder.generalSchedule;
        this.schedules = builder.schedules;
        this.classApprovals = builder.classApprovals;
        this.additionalMaterials = builder.additionalMaterials;
    }
}
