package htms.service;


import htms.api.request.QuestionMarkRequest;
import htms.api.request.QuizQuestionRequest;
import htms.api.response.QuizQuestionResponse;

import java.util.List;
import java.util.UUID;

public interface QuizQuestionService {

    QuizQuestionResponse getDetail(UUID id);

    List<QuizQuestionResponse> addQuestion(QuizQuestionRequest request) throws Exception;

    QuizQuestionResponse removeQuestion(UUID id);

    QuizQuestionResponse updateQuestionMark(QuestionMarkRequest request);

}
