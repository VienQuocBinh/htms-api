package htms.api.domain;

import htms.api.response.CycleResponse;
import htms.api.response.ProgramResponse;
import htms.api.response.TrainerResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassFormData {
    private List<ProgramResponse> programs;
    private List<TrainerResponse> trainers;
    private List<CycleResponse> cycles;
}
