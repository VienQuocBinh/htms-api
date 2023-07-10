package htms.service;

import htms.api.request.QuestionCategoryRequest;
import htms.api.response.QuestionCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionCategoryService {

    List<QuestionCategoryResponse> getAllByTrainerAndProgram(UUID trainerId, UUID programId);

    QuestionCategoryResponse getQuestionCategoryDetail(UUID id);

    QuestionCategoryResponse saveNewCategory(QuestionCategoryRequest request) throws Exception;

    QuestionCategoryResponse update(QuestionCategoryRequest request) throws Exception;

    QuestionCategoryResponse delete(UUID id);

}
