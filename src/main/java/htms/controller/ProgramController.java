package htms.controller;

import htms.api.request.ProgramRequest;
import htms.api.response.ProgramResponse;
import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
