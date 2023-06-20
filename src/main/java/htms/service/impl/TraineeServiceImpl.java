package htms.service.impl;

import htms.api.response.TraineeResponse;
import htms.common.constants.EnrollmentStatus;
import htms.repository.TraineeRepository;
import htms.service.TraineeService;
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
    public List<TraineeResponse> getTraineesByClassId(UUID classId, EnrollmentStatus status) {
        // todo: handle exceptions
        var list = traineeRepository.findAllByClassAndEnrollmentStatus(classId, status).orElseThrow();
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
}
