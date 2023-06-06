package htms.api.response;

import htms.common.constance.ClassStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponse {
    private UUID id;
    private String name;
    private ClassStatus status;
    private int quantity;

//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "cycle_id", referencedColumnName = "cycle_id"),
//            @JoinColumn(name = "program_id", referencedColumnName = "program_id")
//    })
//    private ProgramPerCycle programPerCycle;
}
