package htms.model;

import htms.model.embeddedkey.ProgramPerCycleId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramPerCycle {
    @EmbeddedId
    private ProgramPerCycleId id;
    private Date programStartDate;
    private Date programEndDate;

    @OneToMany(mappedBy = "programPerCycle", fetch = FetchType.LAZY)
    private List<ClassSubject> subjects;
    @OneToMany(mappedBy = "programPerCycle", fetch = FetchType.LAZY)
    private List<Test> tests;
}
