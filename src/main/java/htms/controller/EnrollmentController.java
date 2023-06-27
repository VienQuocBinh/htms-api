package htms.controller;

import htms.api.request.EnrollmentRequest;
import htms.api.response.EnrollmentResponse;
import htms.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    private ResponseEntity<EnrollmentResponse> create(@RequestBody @Valid EnrollmentRequest request) {
        return ResponseEntity.ok(enrollmentService.create(request));
    }
}
