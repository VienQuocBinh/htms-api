package htms.model;

import htms.common.constance.PermissionType;
import htms.common.constance.Resource;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PermissionType permission;
    @Enumerated(EnumType.STRING)
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
