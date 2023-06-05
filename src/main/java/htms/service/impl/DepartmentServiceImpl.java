package htms.service.impl;

import htms.api.request.DepartmentRequest;
import htms.api.response.DepartmentResponse;
import htms.model.Department;
import htms.repository.DepartmentRepository;
import htms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final ModelMapper mapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentResponse> getDepartments() {
        return departmentRepository.findAll()
                .stream().map(element -> mapper.map(element, DepartmentResponse.class))
                .toList();
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        var department = Department.builder()
                .name("Khoa Nhi")
                .build();
        departmentRepository.save(department);
        return mapper.map(department, DepartmentResponse.class);
    }
}
