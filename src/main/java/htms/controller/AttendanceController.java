package htms.controller;

import htms.api.response.TraineeAttendanceResponse;
import htms.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/class/{id}")
    @Operation(summary = "Create attendances of a class")
    private ResponseEntity<String> createAttendanceOfClass(@PathVariable UUID id) {
        attendanceService.createAttendancesOfClass(id);
        return ResponseEntity.status(201).body("Created");
    }

    @GetMapping("/class/{id}")
    @Operation(summary = "Get attendances of a class")
    private ResponseEntity<List<TraineeAttendanceResponse>> getAttendanceOfClass(@PathVariable UUID id) {
        return ResponseEntity.ok(attendanceService.getAttendancesByClassId(id));
    }

    @GetMapping("/trainee/{id}")
    @Operation(summary = "Get attendances of a trainee")
    private ResponseEntity<List<TraineeAttendanceResponse>> getAttendanceOfTrainee(@PathVariable UUID id) {
        return ResponseEntity.ok(attendanceService.getAttendancesByTraineeId(id));
    }
}
