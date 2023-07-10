package htms.model;

import htms.common.constants.AssignmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    private Double grade; // assignment total grade
    private Date startDate; // assignment start date
    private Date dueDate; // assignment due date
    @Column(length = 1000)
    private String attachments; // assignment attachments (URL)
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    @OneToOne
    private TopicSlot slot;
    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY)
    private List<AssignmentSubmission> assignmentSubmissions;
}
