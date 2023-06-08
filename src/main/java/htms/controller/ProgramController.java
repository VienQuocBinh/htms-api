package htms.controller;

import htms.api.request.ProgramRequest;
import htms.api.response.ProgramResponse;
import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/program")
public class ProgramController {
    private final ProgramService programService;

    @PostMapping
    public ResponseEntity<ProgramResponse> create(ProgramRequest request) {
        return ResponseEntity.status(201).body(programService.createProgram(request));
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponse>> getPrograms() {
        return ResponseEntity.ok(programService.getPrograms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponse> getProgramDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(programService.getProgramDetails(id));
    }
}
