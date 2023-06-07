package htms.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private Short moduleNo;
    @URL
    private String link;

    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private List<Session> sessions;
}
