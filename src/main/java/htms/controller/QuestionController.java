package htms.controller;

import htms.api.request.QuestionRequest;
import htms.api.response.QuestionResponse;
import htms.service.QuestionService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank")
public class QuestionController {

    private final QuestionService service;

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getListOfQuestionsByCategoryAndListTags(
            @RequestParam @NotNull UUID categoryId,
            @RequestParam(required = false) String tags
    ) throws Exception {
        List<String> tagsArray;
        if (tags == null || tags.isEmpty()) {
            tagsArray = Collections.emptyList();
        } else {
            tagsArray = Arrays.stream(tags.split(", ")).toList();
        }
        return ResponseEntity.ok(service.getListOfQuestionsInBank(categoryId, tagsArray));
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<QuestionResponse> getQuestionDetail(
            @PathVariable UUID question_id
    ) {
        return ResponseEntity.ok(service.getQuestionDetail(question_id));
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> saveNewQuestion(
            @RequestBody QuestionRequest request
    ) throws Exception {
        return new ResponseEntity<>(service.saveNewQuestion(request), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<QuestionResponse> updateNewQuestion(
            @RequestBody QuestionRequest request
    ) throws Exception {
        return ResponseEntity.ok(service.updateQuestion(request));
    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<QuestionResponse> deleteQuestion(
            @PathVariable UUID question_id
    ) {
        return ResponseEntity.ok(service.delete(question_id));
    }
}
