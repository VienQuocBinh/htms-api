package htms.controller;

import htms.api.request.TrainerRequest;
import htms.api.response.TrainerResponse;
import htms.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<TrainerResponse>> getTrainers() {
        return ResponseEntity.ok(trainerService.getTrainers());
    }

    @PostMapping
    public ResponseEntity<TrainerResponse> createAccount(@RequestBody @Valid TrainerRequest request) {
        return ResponseEntity.status(201).body(trainerService.createTrainer(request));
    }
}
