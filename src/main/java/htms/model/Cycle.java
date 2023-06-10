package htms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private Integer duration; // months
//    private Date startDate;
//    private Date endDate;
//    private Date vacationStartDate;
//    private Date vacationEndDate;

    @OneToMany(mappedBy = "cycle", fetch = FetchType.LAZY)
    private List<ProgramPerClass> programPerClasses;
}
