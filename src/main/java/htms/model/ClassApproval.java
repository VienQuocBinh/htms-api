package htms.model;

import htms.common.constants.ClassApprovalStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
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
public class ClassApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String comment;
    @Enumerated(EnumType.STRING)
    private ClassApprovalStatus status;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdDate;
    @CreatedBy
    private UUID createdBy;
    private Date approvalDate;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz;
}
