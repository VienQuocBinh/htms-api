package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class ChangedSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private UUID changedBy;
    private String reason;
    private String comment;
    @NotNull
    private UUID fromTrainer;
    @NotNull
    private UUID toTrainer;
    @NotNull
    private Date fromDate;
    @NotNull
    private Date toDate;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;

    @OneToOne(mappedBy = "changedSchedule")
    private Schedule schedule;
}
