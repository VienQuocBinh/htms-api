package htms.service;

import htms.api.request.QuizQuestionRequest;
import htms.api.request.QuizRequest;
import htms.api.response.QuizQuestionResponse;
import htms.api.response.QuizResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public interface QuizService {

    QuizResponse getQuizDetail(UUID id);

    QuizResponse createQuiz(QuizRequest request) throws Exception;

    QuizResponse updateQuiz(QuizRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    QuizResponse deleteQuiz(UUID id);

}
