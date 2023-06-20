package htms.controller;

import htms.api.response.TraineeResponse;
import htms.common.constants.EnrollmentStatus;
import htms.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    @GetMapping("/class/{id}")
    public ResponseEntity<List<TraineeResponse>> getTraineeByClass(
            @PathVariable UUID id,
            @Param("status") EnrollmentStatus status) {
        return ResponseEntity.ok(traineeService.getTraineesByClassId(id, status));
    }
}
