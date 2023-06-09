package htms.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityAuditing {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdDate;
    @CreatedBy
    @Column(updatable = false, nullable = false)
    private UUID createdBy;
    @LastModifiedDate
    private Date updatedDate;
    @LastModifiedBy
    private UUID updatedBy;
    @Builder.Default
    private boolean isDeleted = false;

    protected BaseEntityAuditing(BaseEntityAuditingBuilder<?, ?> builder) {
        this.createdBy = builder.createdBy;
        this.createdDate = builder.createdDate;
        this.updatedDate = builder.updatedDate;
        this.updatedBy = builder.updatedBy;
    }
}
