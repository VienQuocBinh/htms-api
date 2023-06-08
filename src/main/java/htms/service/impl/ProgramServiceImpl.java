package htms.service.impl;

import htms.api.request.ProgramRequest;
import htms.api.response.ProgramResponse;
import htms.common.mapper.ProgramMapper;
import htms.model.Program;
import htms.repository.ProgramRepository;
import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
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
}
