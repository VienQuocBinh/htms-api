package htms.controller;

import htms.api.request.ActivityRequest;
import htms.api.response.ActivityResponse;
import htms.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/activity")
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> saveNewActivity(@Valid @RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityService.saveNewActivity(request));
    }

}
