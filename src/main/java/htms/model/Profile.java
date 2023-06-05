package htms.model;

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
public class Profile extends BaseEntityAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status;

    @OneToOne(mappedBy = "profile")
    private Trainee trainee;

    protected Profile(ProfileBuilder<?, ?> builder) {
        super(builder);
        this.id = builder.id;
        this.status = builder.status;
        this.trainee = builder.trainee;
    }
}
