package htms.service.impl;

import htms.api.request.ProgramPerClassRequest;
import htms.api.response.ProgramPerClassResponse;
import htms.model.Class;
import htms.model.Cycle;
import htms.model.Program;
import htms.model.ProgramPerClass;
import htms.model.embeddedkey.ProgramPerClassId;
import htms.repository.ProgramPerClassRepository;
import htms.service.ClassService;
import htms.service.CycleService;
import htms.service.ProgramPerClassService;
import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramPerClassServiceImpl implements ProgramPerClassService {
    private final ProgramPerClassRepository programPerClassRepository;
    private final ModelMapper mapper;
    private final ProgramService programService;
    private final CycleService cycleService;
    private final ClassService classService;

    @Override
    public List<ProgramPerClassResponse> getList() {
        return programPerClassRepository.findAll()
                .stream().map(element -> mapper.map(element, ProgramPerClassResponse.class))
                .toList();
    }

    @Override
    public ProgramPerClass create(ProgramPerClassRequest request) {
        var programPerClass = ProgramPerClass.builder()
                .id(ProgramPerClassId.builder()
                        .program(mapper
                                .map(
                                        programService.getProgramDetails(request.getProgramId()),
                                        Program.class))
                        .clazz(mapper.map(
                                classService.getClassDetail(request.getClassId()),
                                Class.class))
                        .build())
                .cycle(mapper
                        .map(
                                cycleService.getCycleDetails(request.getCycleId()),
                                Cycle.class))
                .programStartDate(request.getProgramStartDate())
                .programEndDate(request.getProgramEndDate())
                .minQuantity(request.getMinQuantity())
                .maxQuantity(request.getMaxQuantity())
                .build();

        return programPerClassRepository.save(programPerClass);
    }
}
