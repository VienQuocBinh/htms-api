package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"number_id","program_id"})
        }
)
public class QuestionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull(message = "question category name is required")
    @Column(length = 100, nullable = false)
    @Length(max = 100, message = "Name cannot exceed 100 characters")
    private String name;
    @Column(name = "number_id", length = 100)
    @Length(max = 100, message = "number id cannot exceed 100 characters")
    private String numberId;
    @Column(name = "description")
    private String description;
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;
    @LastModifiedDate
    @Column(name = "modified_date")
    private Date modifiedDate;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<QuestionCategory> child;
    @ManyToOne
    @JoinColumn(name = "parent")
    private QuestionCategory parent;
    @OneToMany(mappedBy = "questionCategory", fetch = FetchType.LAZY)
    private List<Question> questions;

//    public QuestionCategory(BaseEntityAuditingBuilder<?, ?> builder, UUID id,@NotNull String name, String description, Date createdDate, Date modifiedDate) {
//        super(builder);
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
//    }
}
