package htms.model;

import htms.common.constants.TopicSlotType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "((resource IS NOT NULL AND quiz IS NULL) " +
                     "OR (quiz IS NOT NULL AND resource IS NULL))")
public class TopicSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer position;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(10)", nullable = false, updatable = false)
    private TopicSlotType type;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ExternalResource externalResource;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Quiz quiz;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Assignment assignment;
    @Builder.Default
    private Boolean isHidden = false;
}
