package htms.controller;

import htms.api.request.QuizQuestionRequest;
import htms.api.request.QuizRequest;
import htms.api.response.QuizQuestionResponse;
import htms.api.response.QuizResponse;
import htms.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic/quiz")
public class QuizController {

    private final QuizService service;

    @GetMapping("/{quiz_id}")
    public ResponseEntity<QuizResponse> getQuizDetail(@PathVariable UUID quiz_id) {
        return ResponseEntity.ok(service.getQuizDetail(quiz_id));
    }

    @PostMapping
    public ResponseEntity<QuizResponse> createNewQuiz(@RequestBody QuizRequest request) throws Exception {
        return new ResponseEntity<>(service.createQuiz(request), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<QuizResponse> updateQuiz(@RequestBody QuizRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return ResponseEntity.ok(service.updateQuiz(request));
    }

    @DeleteMapping("/{quiz_id}")
    public ResponseEntity<QuizResponse> delete(@PathVariable UUID quiz_id) {
        return ResponseEntity.ok(service.deleteQuiz(quiz_id));
    }


}
