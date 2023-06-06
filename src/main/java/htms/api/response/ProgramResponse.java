package htms.api.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramResponse {
    // program start/end date
    private UUID id;
    private String description;
    private String code;

//    private Department department;
//    private List<ProgramPerCycle> programPerCycles;
}
