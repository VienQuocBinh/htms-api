package htms.service.impl;

import htms.api.response.TraineeResponse;
import htms.repository.TraineeRepository;
import htms.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final ModelMapper mapper;

    @Override
    public List<TraineeResponse> getTraineesByClassId(UUID classId) {
        // todo: handle exceptions
        var list = traineeRepository.findAllByClassAndEnrollmentStatus(classId).orElseThrow();
        return list.stream()
                .map(trainees -> mapper.map(trainees, TraineeResponse.class))
                .toList();
    }

    @Override
    public List<TraineeResponse> getTrainees() {
        return traineeRepository.findAll().stream()
                .map(trainees -> mapper.map(trainees, TraineeResponse.class))
                .toList();
    }

    @Override
    public TraineeResponse getTrainee(UUID traineeId) {
        // todo: handle exceptions
        return mapper.map(traineeRepository.findById(traineeId)
                        .orElseThrow(EntityNotFoundException::new),
                TraineeResponse.class);
    }
}
