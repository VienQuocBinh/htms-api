package htms.service.impl;

import htms.api.request.CycleRequest;
import htms.api.response.CycleResponse;
import htms.common.exception.EntityNotFoundException;
import htms.model.Cycle;
import htms.repository.CycleRepository;
import htms.service.CycleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CycleServiceImpl implements CycleService {
    private final CycleRepository cycleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CycleResponse> getCycles() {
        return cycleRepository.findAll()
                .stream().map(element -> modelMapper.map(element, CycleResponse.class))
                .toList();
    }

    @Override
    public CycleResponse getCycleDetails(UUID id) {
        return modelMapper.map(
                cycleRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Cycle.class, "id", id)),
                CycleResponse.class);
    }

    @Override
    public CycleResponse createCycle(CycleRequest request) {
        var cycle = Cycle.builder()
                .description("Spring 2023")
//                .startDate(new Date())
//                .endDate(new Date())
//                .vacationEndDate(new Date())
//                .vacationStartDate(new Date())
                .duration(18)
                .build();
        cycleRepository.save(cycle);
        return modelMapper.map(cycle, CycleResponse.class);
    }
}
