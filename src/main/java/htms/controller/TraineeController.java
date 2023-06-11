package htms.controller;

import htms.api.response.TraineeResponse;
import htms.service.TraineeService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/class/{classId}/program/{programId}")
    public ResponseEntity<List<TraineeResponse>> getTraineeByClass(
            @PathVariable UUID classId,
            @PathVariable UUID programId) {
        return ResponseEntity.ok(traineeService.getTraineesByClassId(classId, programId));
    }
}
