package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Account> accounts;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Permission> permissions;

    @PreRemove
    private void preRemove() {
        for (Account account : accounts) account.setDepartment(null);
    }
}
