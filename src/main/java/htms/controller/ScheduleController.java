package htms.controller;

import htms.api.response.ScheduleResponse;
import htms.service.ScheduleService;
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
@RequestMapping("/api/v1/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/class/{id}")
    public ResponseEntity<List<ScheduleResponse>> getScheduleOfClass(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleService.getScheduleOfClass(id));
    }

    @GetMapping("/trainer/{id}")
    public ResponseEntity<List<ScheduleResponse>> getScheduleOfTrainer(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleService.getScheduleOfTrainer(id));
    }
}
