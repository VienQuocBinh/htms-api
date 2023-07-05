package htms.service;

import htms.api.domain.OverlappedSchedule;
import htms.api.request.TrainerRequest;
import htms.api.response.TrainerResponse;

import java.util.List;
import java.util.UUID;

public interface TrainerService {
    TrainerResponse createTrainer(TrainerRequest request);

    List<TrainerResponse> getTrainers();

    TrainerResponse getTrainer(UUID id);

    OverlappedSchedule getOverlappedScheduleOfTrainer(UUID id, String generalSchedule);
}
