package htms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 1000)
    private String link;
    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz;
}
