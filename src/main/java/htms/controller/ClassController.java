package htms.controller;

import htms.api.domain.CreateClassFormData;
import htms.api.request.ClassRequest;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;
import htms.service.ClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
public class ClassController {
    private final ClassService classService;

    @GetMapping
    public ResponseEntity<List<ClassResponse>> getClasses() {
        return ResponseEntity.ok(classService.getClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponse> getClassDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(classService.getClassDetail(id));
    }

    @GetMapping("/form")
    public ResponseEntity<CreateClassFormData> getCreateClassFormData() {
        return ResponseEntity.ok(classService.getCreateClassFormData());
    }

    @PostMapping
    public ResponseEntity<ClassResponse> createClass(@RequestBody ClassRequest request) {
        return ResponseEntity.status(201).body(classService.createClass(request));
    }
    @GetMapping("/search")
    public ResponseEntity<List<ClassesApprovalResponse>> searchClasses(
            @Valid @RequestParam(required = false) Optional<String> q,
            @Valid @RequestParam(required = false, defaultValue = "PENDING") ClassApprovalStatus status,
            @Valid @RequestParam(required = false, defaultValue = "CREATED_DATE") SortBy sortBy,
            @Valid @RequestParam(required = false, defaultValue = "DESC") SortDirection direction
    ){
        return ResponseEntity.ok(classService.searchClasses(q.orElse(""), status, sortBy, direction));
    }
}
