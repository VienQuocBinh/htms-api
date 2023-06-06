package htms.service.impl;

import htms.api.request.ProgramPerCycleRequest;
import htms.api.response.ProgramPerCycleResponse;
import htms.model.ProgramPerCycle;
import htms.model.embeddedkey.ProgramPerCycleId;
import htms.repository.CycleRepository;
import htms.repository.ProgramPerCycleRepository;
import htms.repository.ProgramRepository;
import htms.service.ProgramPerCycleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramPerCycleServiceImpl implements ProgramPerCycleService {
    private final ProgramPerCycleRepository programPerCycleRepository;
    private final CycleRepository cycleRepository;
    private final ProgramRepository programRepository;
    private final ModelMapper mapper;

    @Override
    public List<ProgramPerCycleResponse> getList() {
        return programPerCycleRepository.findAll()
                .stream().map(element -> mapper.map(element, ProgramPerCycleResponse.class))
                .toList();
    }

    @Override
    public ProgramPerCycleResponse create(ProgramPerCycleRequest request) {
        var programPerCycle = ProgramPerCycle.builder()
                .id(ProgramPerCycleId.builder()
                        .cycle(cycleRepository.findById(request.getCycleId()).get())
                        .program(programRepository.findById(request.getProgramId()).get())
                        .build())
                .programStartDate(request.getProgramStartDate())
                .programEndDate(request.getProgramEndDate())
                .build();
        programPerCycleRepository.save(programPerCycle);
        return mapper.map(programPerCycle, ProgramPerCycleResponse.class);
    }
}
