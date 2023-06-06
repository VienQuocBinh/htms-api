package htms.controller;

import htms.api.request.ClassRequest;
import htms.api.response.ClassResponse;
import htms.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
public class ClassController {
    private final ClassService classService;

    @GetMapping
    public ResponseEntity<List<ClassResponse>> getClasses() {
        return ResponseEntity.ok(classService.getClasses());
    }

    @PostMapping
    public ResponseEntity<ClassResponse> createClass(ClassRequest request) {
        return ResponseEntity.status(201).body(classService.createClass(request));
    }
}
