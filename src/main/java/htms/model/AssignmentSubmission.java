package htms.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Double grade; // submission grade
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;
    @Column(length = 1000)
    private String fileLink; // submission (URL)

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}
