package htms.controller;

import htms.api.request.ClassRequest;
import htms.api.response.ClassResponse;
import htms.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public ResponseEntity<ClassResponse> createClass(ClassRequest request) {
        return ResponseEntity.status(201).body(classService.createClass(request));
    }
}
