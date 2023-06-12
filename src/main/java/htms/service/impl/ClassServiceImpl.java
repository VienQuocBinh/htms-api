package htms.service.impl;

import htms.api.request.ClassRequest;
import htms.api.request.ProgramPerClassRequest;
import htms.api.response.ClassResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.mapper.ClassMapper;
import htms.model.Class;
import htms.model.ClassApproval;
import htms.repository.ClassApprovalRepository;
import htms.repository.ClassRepository;
import htms.service.ClassService;
import htms.service.ProgramPerClassService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ModelMapper modelMapper;
    private final ClassRepository classRepository;
    private final ClassApprovalRepository classApprovalRepository;
    private final ClassMapper classMapper;
    private ProgramPerClassService programPerClassService;

    // DI using setter to avoid circular dependency
    @Autowired
    public void setProgramPerClassService(@Lazy ProgramPerClassService programPerClassService) {
        this.programPerClassService = programPerClassService;
    }

    @Override
    public ClassResponse createClass(ClassRequest request) {
        // create the class
        // todo: generate class code, assign created by
        var clazz = Class.builder()
                .name(request.getName())
                .code("TEST")
                .createdBy(UUID.randomUUID())
                .generalSchedule(request.getGeneralSchedule())
                .build();
        classRepository.save(clazz);
        // create program per class
        var programPerClass = programPerClassService.create(ProgramPerClassRequest.builder()
                .programId(request.getProgramId())
                .classId(clazz.getId())
                .cycleId(request.getCycleId())
                .programStartDate(request.getStartDate())
                .programEndDate(request.getEndDate())
                .minQuantity(request.getMinQuantity())
                .maxQuantity(request.getMaxQuantity())
                .build());
        // create class approval with PENDING status
        // todo: assign created by
        var classApproval = ClassApproval.builder()
                .clazz(clazz)
                .status(ClassApprovalStatus.PENDING)
                .build();
        classApprovalRepository.save(classApproval);

        return classMapper.toResponse(clazz, classApproval, programPerClass);
    }

    @Override
    public List<ClassResponse> getClasses() {
        return classRepository.findAll()
                .stream().map((element) -> modelMapper.map(element, ClassResponse.class))
                .toList();
    }

    @Override
    public ClassResponse getClassDetail(UUID id) {
        // todo: handle exceptions
        return modelMapper.map(
                classRepository.findById(id).orElseThrow(),
                ClassResponse.class);
    }
}
