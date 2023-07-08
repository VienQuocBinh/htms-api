package htms.service;


import htms.api.request.QuestionCategoryRequest;
import htms.api.request.QuestionRequest;
import htms.api.response.QuestionResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    List<QuestionResponse> getListOfQuestionsInBank(UUID categoryId, List<String> tags) throws Exception;

    QuestionResponse getQuestionDetail(UUID id);

    QuestionResponse saveNewQuestion(QuestionRequest request) throws Exception;

    QuestionResponse updateQuestion(QuestionRequest request) throws Exception;

    QuestionResponse delete(UUID id);
}
