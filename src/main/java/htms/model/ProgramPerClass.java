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
    private Date programStartDate;
    private Date programEndDate;

    //    @OneToMany(mappedBy = "programPerClass", fetch = FetchType.LAZY)
//    private List<Class> classes;
    @OneToMany(mappedBy = "programPerClass", fetch = FetchType.LAZY)
    private List<Test> tests;
    @OneToMany(mappedBy = "id.programPerClass", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    @OneToMany(mappedBy = "programPerClass")
    private List<AdditionalMaterial> additionalMaterials;
    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;
}
