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
    @Column(length = 1000)
    private String description;
    private Integer moduleNo;
    @URL
    @Column(length = 1000)
    private String link;

    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    private Syllabus syllabus;
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private List<Session> sessions;
}
