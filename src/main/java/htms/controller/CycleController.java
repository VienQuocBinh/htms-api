package htms.controller;

import htms.api.request.CycleRequest;
import htms.api.response.CycleResponse;
import htms.service.CycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cycle")
public class CycleController {
    private final CycleService cycleService;

    @GetMapping
    public ResponseEntity<List<CycleResponse>> getCycles() {
        return ResponseEntity.ok(cycleService.getCycles());
    }

    @PostMapping
    public ResponseEntity<CycleResponse> createCycle(CycleRequest request) {
        return ResponseEntity.status(201).body(cycleService.createCycle(request));
    }
}
