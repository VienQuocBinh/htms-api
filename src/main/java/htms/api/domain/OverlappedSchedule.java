package htms.api.domain;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OverlappedSchedule {
    private UUID id; // trainer,trainee
    private List<String> overlappedDayTimes;
}
