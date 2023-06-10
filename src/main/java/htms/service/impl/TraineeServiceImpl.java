package htms.service.impl;

import htms.api.response.TraineeResponse;
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
    public List<TraineeResponse> getTraineesByClassId(UUID classId) {
        // todo: handle exceptions
//        var list = traineeRepository.findAllByClassId(classId).orElseThrow();
//        return list.stream()
//                .map(trainees -> mapper.map(trainees, TraineeResponse.class))
//                .toList();

        return null;
    }
}
