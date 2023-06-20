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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ModelMapper mapper;
    private final ModelMapper modelMapper;

    @Override
    public List<ProgramResponse> getPrograms() {
        return programRepository.findAll()
                .stream()
                .map((element) -> modelMapper.map(element, ProgramResponse.class))
                .toList();
    }

    @Override
    public ProgramResponse getProgramDetails(UUID id) {
        // todo: handle exceptions
        return programRepository.findById(id)
                .map((element) -> mapper.map(element, ProgramResponse.class))
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
