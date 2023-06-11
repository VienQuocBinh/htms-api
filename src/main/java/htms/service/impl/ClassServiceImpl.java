package htms.service.impl;

import htms.api.request.ClassRequest;
import htms.api.response.ClassResponse;
import htms.model.Class;
import htms.repository.ClassRepository;
import htms.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClassResponse createClass(ClassRequest request) {
        var clazz = Class.builder()
                .name("Class SE1615")
                .build();
        classRepository.save(clazz);
        return modelMapper.map(clazz, ClassResponse.class);
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
        return classRepository.findById(id)
                .map((element) -> modelMapper.map(element, ClassResponse.class))
                .orElseThrow();
    }
}
