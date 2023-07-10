package htms.controller;

import htms.api.request.TopicRequest;
import htms.api.response.TopicResponse;
import htms.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicController {
    private final TopicService service;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getProgramTopics(
            @RequestParam UUID trainerId,
            @RequestParam UUID programId
            ) {
        return ResponseEntity.ok(service.getProgramTopics(programId, trainerId));
    }

    @PostMapping
    public ResponseEntity<TopicResponse> createNewTopic(@RequestBody TopicRequest request) throws Exception {
        return new ResponseEntity<>(service.createNewTopic(request), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<TopicResponse> updateTopic(@RequestBody TopicRequest request) {
        return ResponseEntity.ok(service.updateTopic(request));
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<TopicResponse> deleteTopic(@PathVariable UUID topicId) throws Exception {
        return ResponseEntity.ok(service.delete(topicId));
    }

}
