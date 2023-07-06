package htms.controller;

import htms.api.response.SyllabusResponse;
import htms.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/syllabus")
public class SyllabusController {
    private final SyllabusService syllabusService;

    @GetMapping("/program/{id}")
    public ResponseEntity<SyllabusResponse> getSyllabuses(@PathVariable UUID id) {
        return ResponseEntity.ok(syllabusService.getSyllabusByProgramId(id));
    }

}
