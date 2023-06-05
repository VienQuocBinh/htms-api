package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Length(min = 4, max = 100)
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Account> accounts;
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Program> programs;

    @PreRemove
    private void preRemove() {
        for (Account account : accounts) account.setDepartment(null);
    }
}
