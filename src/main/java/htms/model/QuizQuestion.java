package htms.model;

import htms.common.constants.QuestionStatus;
import htms.common.constants.QuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "number_id", length = 100)
    @Length(max = 100, message = "number id cannot exceed 100 characters")
    private String numberId;
    @Column(name = "question_type", length = 32, columnDefinition = "varchar(10)")
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    @Column(name = "shuffle", columnDefinition = "boolean")
    private boolean shuffle;
    @Column(name = "name", length = 100)
    @Length(max = 100, message = "question name cannot exceed 100 characters")
    @NotNull(message = "Question name cannot be null")
    private String name;
    @Column(name = "status", columnDefinition = "varchar(16)")
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
    @Column(name = "question_text", columnDefinition = "text")
    @NotNull(message = "question text cannot be null")
    private String questionText;
    @Column(name = "default_mark")
    @NotNull(message = "Default mark cannot be null")
    private Double defaultMark;
    @Column(name = "general_feedback", columnDefinition = "text")
    private String generalFeedBack;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "trainer")
    private Trainer trainer;
    @Column(name = "tags", columnDefinition = "text")
    private String tags;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private QuestionCategory questionCategory;
    @OneToMany(mappedBy = "quizQuestion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuizQuestionAnswer> answers;
    @ManyToOne
    private Quiz quiz;

    private Integer slot; //keep order of the question in the question list;

    // fields for random question
    private boolean isRandomQuestion;
    @ManyToOne(cascade = CascadeType.DETACH)
    private QuestionCategory targetCategory;
    private String targetTags;
}
