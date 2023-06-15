package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String materialLink;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<Assignment> assignments;
    @OneToOne(mappedBy = "activity")
    private Test test;
}
