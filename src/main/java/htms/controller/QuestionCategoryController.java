package htms.controller;

import htms.api.request.QuestionCategoryRequest;
import htms.api.response.QuestionCategoryResponse;
import htms.service.QuestionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank/category")
public class QuestionCategoryController {

    private final QuestionCategoryService service;

    @GetMapping
    public ResponseEntity<List<QuestionCategoryResponse>> getAllByTrainerAndProgram(
            @RequestParam UUID trainerId,
            @RequestParam UUID programId) {
        return ResponseEntity.ok(service.getAllByTrainerAndProgram(trainerId, programId));
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<QuestionCategoryResponse> getCategoryDetail(
            @PathVariable UUID category_id) {
        return new ResponseEntity<>(service.getQuestionCategoryDetail(category_id), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<QuestionCategoryResponse> save(
            @RequestBody QuestionCategoryRequest request) throws Exception {
        return ResponseEntity.ok(service.saveNewCategory(request));
    }

    @PatchMapping
    public ResponseEntity<QuestionCategoryResponse> update(
            @RequestBody QuestionCategoryRequest request) throws Exception {
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<QuestionCategoryResponse> delete(
            @PathVariable UUID category_id) {
        return ResponseEntity.ok(service.delete(category_id));
    }

}
