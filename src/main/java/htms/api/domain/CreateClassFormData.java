package htms.api.domain;

import htms.api.response.CycleResponse;
import htms.api.response.ProgramResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassFormData {
    private List<ProgramResponse> programs;
    private List<CycleResponse> cycles;
}
