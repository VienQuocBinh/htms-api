package htms.controller;

import htms.api.request.EnrollmentRequest;
import htms.api.response.EnrollmentResponse;
import htms.common.constants.EnrollmentStatus;
import htms.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "For trainee to enrol to the class")
    private ResponseEntity<EnrollmentResponse> enrol(@RequestBody @Valid EnrollmentRequest request) {
        return ResponseEntity.ok(enrollmentService.enrol(request));
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<List<EnrollmentResponse>> getClassEnrollments(@PathVariable UUID id,
                                                                        @RequestParam(required = false, defaultValue = "PENDING") EnrollmentStatus status) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentByClassIdAndStatus(id, status));
    }
}
