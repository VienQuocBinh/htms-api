package htms.common.mapper;

import htms.api.response.ClassResponse;
import htms.model.Class;
import htms.model.ClassApproval;
import htms.model.ProgramPerClass;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassMapper {
    private final ModelMapper mapper;

    public ClassResponse toResponse(Class clazz, ClassApproval classApproval, ProgramPerClass programPerClass) {
        var response = mapper.map(clazz, ClassResponse.class);
        response.setStatus(classApproval.getStatus());
        response.setMinQuantity(programPerClass.getMinQuantity());
        response.setMaxQuantity(programPerClass.getMaxQuantity());
        response.setProgramId(programPerClass.getId().getProgram().getId());
        response.setCycleId(programPerClass.getCycle().getId());
        response.setStartDate(programPerClass.getProgramStartDate());
        response.setEndDate(programPerClass.getProgramEndDate());
        return response;
    }

    public Class toModel(ClassResponse classResponse) {
        return Class.builder()
                .id(classResponse.getId())
                .name(classResponse.getName())
                .code(classResponse.getCode())
                .generalSchedule(classResponse.getGeneralSchedule())
                .quantity(0)
                .build();
    }
}
