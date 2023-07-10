package htms.service.impl;

import htms.api.request.QuestionMarkRequest;
import htms.api.request.QuizQuestionRequest;
import htms.api.response.QuizQuestionResponse;
import htms.model.Quiz;
import htms.model.QuizQuestion;
import htms.repository.QuestionCategoryRepository;
import htms.repository.QuestionRepository;
import htms.repository.QuizQuestionRepository;
import htms.repository.QuizRepository;
import htms.service.QuizQuestionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {

    private final QuizQuestionRepository repository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionCategoryRepository questionCategoryRepository;
    private final ModelMapper mapper;

    @Override
    public QuizQuestionResponse getDetail(UUID id) {
        var quizQuestion = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.map(quizQuestion, QuizQuestionResponse.class);
    }

    @Override
    public List<QuizQuestionResponse> addQuestion(QuizQuestionRequest request) throws Exception {
        var quiz = quizRepository.findById(request.getQuizId()).orElseThrow(EntityNotFoundException::new);
        List<QuizQuestion> quizQuestions;
        if(request.getIsRandom()) {
            if(request.getCategoryId() == null) {
                throw new Exception("categoryId must not be null");
            }
            quizQuestions = addRandomQuestion(request, quiz);
        } else {
            quizQuestions = addQuestions(request, quiz);
        }
        quiz.getQuestions().addAll(quizQuestions);
        quizRepository.save(quiz);
        return quizQuestions.stream().map(quizQuestion -> mapper.map(quizQuestion, QuizQuestionResponse.class)).toList();
    }


    private List<QuizQuestion> addQuestions(QuizQuestionRequest request, Quiz quiz) {
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        var questions = quiz.getQuestions();
        questions.sort(Comparator.comparing(QuizQuestion::getSlot));
        var lastQuestion = questions.get(questions.size() - 1).getSlot();
        var selectedQuestions = request.getSelected();
        for (int i = 0; i < selectedQuestions.size(); i++) {
            var question = questionRepository.findById(selectedQuestions.get(i)).orElseThrow(EntityNotFoundException::new);
            var newQuizQuestion = mapper.map(question, QuizQuestion.class);
            newQuizQuestion.setSlot(lastQuestion + i + 1);
            quizQuestions.add(newQuizQuestion);
        }
        quizQuestions.sort(Comparator.comparing(QuizQuestion::getSlot));
        return quizQuestions;
    }

    private List<QuizQuestion> addRandomQuestion(QuizQuestionRequest request, Quiz quiz) {
        var questions = quiz.getQuestions();
        questions.sort(Comparator.comparing(QuizQuestion::getSlot));
        var lastQuestion = questions.get(questions.size() - 1).getSlot();
        lastQuestion++;
        List<QuizQuestion> newQuestions = new ArrayList<>();
        var category = questionCategoryRepository.findById(request.getCategoryId()).orElseThrow(EntityNotFoundException::new);
        for (int i = lastQuestion; i < lastQuestion + request.getQuantity(); i++) {
            var newRandomQuestion = QuizQuestion.builder()
                    .slot(i)
                    .isRandomQuestion(true)
                    .targetCategory(category)
                    .targetTags(String.join(", ", request.getTags()));
            newQuestions.add(newRandomQuestion.build());
        }
        return newQuestions;
    }

    @Override
    public QuizQuestionResponse removeQuestion(UUID id) {
        var quizQuestion = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(quizQuestion);
        return mapper.map(quizQuestion, QuizQuestionResponse.class);
    }

    @Override
    public QuizQuestionResponse updateQuestionMark(QuestionMarkRequest request) {
        var quizQuestion = repository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        quizQuestion.setDefaultMark(request.getMark());
        quizQuestion = repository.save(quizQuestion);
        return mapper.map(quizQuestion, QuizQuestionResponse.class);
    }
}
