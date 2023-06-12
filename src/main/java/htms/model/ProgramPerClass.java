package htms.model;

import htms.model.embeddedkey.ProgramPerClassId;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramPerClass {
    @EmbeddedId
    private ProgramPerClassId id;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Date programStartDate;
    private Date programEndDate;

    @OneToMany(mappedBy = "programPerClass", fetch = FetchType.LAZY)
    private List<Test> tests;
    @OneToMany(mappedBy = "id.programPerClass", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;
}
