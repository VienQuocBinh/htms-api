package htms.api.request;

import jakarta.validation.constraints.Max;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {
    private String name;
    @Max(30)
    private int quantity;

//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "cycle_id", referencedColumnName = "cycle_id"),
//            @JoinColumn(name = "program_id", referencedColumnName = "program_id")
//    })
//    private ProgramPerCycle programPerCycle;
}
