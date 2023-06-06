package htms.service.impl;

import htms.api.request.ProgramRequest;
import htms.api.response.ProgramResponse;
import htms.model.Program;
import htms.repository.ProgramRepository;
import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ModelMapper mapper;

    @Override
    public List<ProgramResponse> getPrograms() {
        return programRepository.findAll()
                .stream().map(element -> mapper.map(element, ProgramResponse.class))
                .toList();
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
