package htms.service.impl;

import htms.api.request.ProgramRequest;
import htms.api.response.ProgramResponse;
import htms.api.response.TopicResponse;
import htms.api.response.TrainerResponse;
import htms.common.mapper.ProgramMapper;
import htms.model.Program;
import htms.repository.ClassRepository;
import htms.repository.ProgramContentRepository;
import htms.repository.ProgramRepository;
import htms.service.ProgramService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final ClassRepository classRepository;
    private final ProgramContentRepository contentRepository;
    private final ModelMapper mapper;

    @Override
    public List<ProgramResponse> getPrograms() {
        return programRepository.findAll()
                .stream().map(programMapper::toResponse)
                .toList();
    }

    @Override
    public ProgramResponse getProgramDetails(UUID id) {
        // todo: handle exceptions
        return programRepository.findById(id)
                .map(programMapper::toResponse)
                .orElseThrow();
    }

    @Override
    public ProgramResponse createProgram(ProgramRequest request) {
        var program = Program.builder()
                .description("Coursera program")
                .code("SP23")
                .build();
        programRepository.save(program);

        return mapper.map(program, ProgramResponse.class);
    }

    @Override
    public List<ProgramResponse> getProgramsTheTraineeIsTaking(UUID traineeId) {
        var classes = classRepository.findAllCurrentTakingClassesByTrainee(traineeId).orElse(Collections.emptyList());
        return classes.stream().map(c -> {
            var model = programMapper.toResponse(c.getProgram());
            model.setTrainer(mapper.map(c.getTrainer(), TrainerResponse.class));
            return model;
        }).toList();
    }

    @Override
    public ProgramResponse getProgramContent(UUID programId, UUID trainerId) {
        var content = contentRepository.findById_Program_IdAndId_Trainer_Id(programId, trainerId).orElseThrow(EntityNotFoundException::new);
        var response = programMapper.toResponse(content.getId().getProgram());
        response.setTopics(content.getTopics().stream().map(topic -> mapper.map(topic, TopicResponse.class)).toList());
        response.setTrainer(mapper.map(content.getId().getTrainer(), TrainerResponse.class));
        return response;
    }
}
