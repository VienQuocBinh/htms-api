package htms.common.mapper;

import htms.api.response.ProgramPerClassResponse;
import htms.api.response.ProgramResponse;
import htms.model.Program;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgramMapper {
    private final ModelMapper mapper;

    public ProgramResponse toResponse(Program program) {
        var response = mapper.map(program, ProgramResponse.class);
        var ppc = program.getProgramPerClasses()
                .stream()
                .map((element) -> mapper.map(element, ProgramPerClassResponse.class))
                .toList();
        response.setProgramPerCycleList(ppc);
        return response;
    }
}