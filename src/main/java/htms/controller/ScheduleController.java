package htms.controller;

import htms.api.domain.OverlappingSchedule;
import htms.api.response.ScheduleResponse;
import htms.service.ScheduleService;
import htms.util.ScheduleUtil;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get Schedules of a class")
    public ResponseEntity<List<ScheduleResponse>> getScheduleOfClass(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleService.getScheduleOfClass(id));
    }

    @GetMapping("/trainer/{id}")
    @Operation(summary = "Get Schedules of a trainer")
    public ResponseEntity<List<ScheduleResponse>> getScheduleOfTrainer(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleService.getScheduleOfTrainer(id));
    }

    @GetMapping("/trainee/{id}")
    @Operation(summary = "Get Schedules of a trainee")
    public ResponseEntity<List<ScheduleResponse>> getScheduleOfTrainee(@PathVariable UUID id) {
        return ResponseEntity.ok(scheduleService.getScheduleOfTrainee(id));
    }

    @GetMapping("/test")
    @Operation(summary = "Dup Schedules")
    public ResponseEntity<OverlappingSchedule> getScheduleOfTrainee() {
        OverlappingSchedule duplicateSchedule = ScheduleUtil.getOverlappingSchedule(
                "Start{10:00,MON};Stop{11:00,MON};Start{17:00,MON};Stop{19:00,MON}",
                "Start{10:30,MON};Stop{12:00,MON};Start{17:00,MON};Stop{19:00,MON}");
        return ResponseEntity.ok(duplicateSchedule);
    }
}
