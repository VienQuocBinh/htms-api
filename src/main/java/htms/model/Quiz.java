package htms.model;

import htms.common.constants.GradingMethod;
import htms.common.constants.TimeUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 1000, nullable = false)
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private Date openTime;
    private Date closeTime;
    private Integer timeLimit;
    private TimeUnit unit;
    private Double gradeToPass;
    private Integer allowedAttempt;
    private GradingMethod gradingMethod;
    private boolean shuffle;
    private boolean reviewable;
    private String password;
    private String tags;
    private Double maxGrade;
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizQuestion> questions;
    @ManyToOne
    private Trainer trainer;
    @LastModifiedDate
    private Date modifiedDate;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;
    @OneToOne
    private TopicSlot slot;
}
