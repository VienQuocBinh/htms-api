package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account extends BaseEntityAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Email
    private String email;
    @NotNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne(mappedBy = "account")
    private Trainee trainee;
    @OneToOne(mappedBy = "account")
    private Trainer trainer;


    protected Account(AccountBuilder<?, ?> builder) {
        super(builder);
        this.id = builder.id;
        this.email = builder.email;
        this.title = builder.title;
        this.department = builder.department;
        this.role = builder.role;
        this.trainee = builder.trainee;
        this.trainer = builder.trainer;
    }
}
