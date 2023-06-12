package htms.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @Nullable
    @URL
    @Column(length = 1000)
    private String link;
    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
}
