package htms.service;

import htms.api.request.TrainerRequest;
import htms.api.response.TrainerResponse;

import java.util.List;

public interface TrainerService {
    TrainerResponse createTrainer(TrainerRequest request);

    List<TrainerResponse> getTrainers();
}
