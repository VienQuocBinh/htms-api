package htms.controller;

import htms.api.response.PageResponse;
import htms.api.response.TraineeResponse;
import htms.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    @GetMapping("/class/{id}")
    public ResponseEntity<List<TraineeResponse>> getTraineeByClass(@PathVariable UUID id) {
        return ResponseEntity.ok(traineeService.getTraineesByClassId(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TraineeResponse>> getTraineesPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filterOr", required = false) String filterOr,
            @RequestParam(value = "filterAnd", required = false) String filterAnd,
            @RequestParam(value = "orders", required = false) String orders
    ) {
        var response = traineeService.getTraineesPage(page, size, filterOr, filterAnd, orders);

        return ResponseEntity.ok(response);
    }
}
