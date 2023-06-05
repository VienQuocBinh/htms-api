package htms.controller;

import htms.api.request.DepartmentRequest;
import htms.api.response.DepartmentResponse;
import htms.repository.DepartmentRepository;
import htms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getDepartments() {
        return ResponseEntity.ok(departmentService.getDepartments());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        departmentRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(DepartmentRequest request) {
        return ResponseEntity.ok(departmentService.createDepartment(request));
    }
}
