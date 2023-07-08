package htms.service.impl;

import htms.api.request.QuestionRequest;
import htms.api.response.QuestionResponse;
import htms.model.Question;
import htms.model.QuestionAnswer;
import htms.repository.QuestionCategoryRepository;
import htms.repository.QuestionRepository;
import htms.repository.TrainerRepository;
import htms.service.QuestionService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository repository;
    private final TrainerRepository trainerRepository;
    private final QuestionCategoryRepository questionCategoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<QuestionResponse> getListOfQuestionsInBank(UUID categoryId, List<String> tags) throws Exception {
        log.info("getting list of questions");
        if (categoryId == null) throw new Exception("categoryId must not be null");
        var questions = repository.GetListOfQuestionByCategoryAndTags(tags, tags.size(), categoryId);
        log.info("questions found with " + questions.size() + " entities");
        return questions.stream().map(question -> {
            var res = modelMapper.map(question, QuestionResponse.class);
            res.setAnswers(null);
            res.setTags(Arrays.stream(question.getTags().split(", ")).toList());
            res.setGeneralFeedback(null);
            return res;
        }).toList();
    }

    @Override
    public QuestionResponse getQuestionDetail(UUID id) {
        log.info("Getting question with ID: " + id.toString());
        var question = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        log.info("Found question with name: " + question.getName());
        return toResponse(question);
    }

    @Override
    public QuestionResponse saveNewQuestion(QuestionRequest request) throws Exception {
        // validate
        // in 1 category cannot have 2 question with the same number id
        var isExisted = repository.existsByNumberIdAndQuestionCategory_Id(request.getNumberId(), request.getCategoryId());
        log.info("Question with numberId " + request.getNumberId() + " already existed in the category");
        if (isExisted) throw new Exception("Number id existed in this category");
        validate(request);
        // validate trainer and category
        var trainer = trainerRepository.findById(request.getTrainerId()).orElseThrow(EntityNotFoundException::new);
        var category = questionCategoryRepository.findById(request.getCategoryId()).orElseThrow(EntityNotFoundException::new);
        // all good. Saving new question
        log.info("Saving new question");
        var question = modelMapper.map(request, Question.class);
        question.setTags(String.join(", ", request.getTags()));
        question.setCreatedDate(Date.from(Instant.now()));
        question.setModifiedDate(Date.from(Instant.now()));
        question.setTrainer(trainer);
        question.setQuestionCategory(category);
        Question finalQuestion = question;
        question.setAnswers(request.getAnswers().stream().map(a -> {
            var answer = modelMapper.map(a, QuestionAnswer.class);
            answer.setQuestion(finalQuestion);
            return answer;
        }).toList());
        question = repository.save(question);
        return toResponse(question);
    }

    @Override
    public QuestionResponse updateQuestion(QuestionRequest request) throws Exception {
        // validate
        // question existed or not
        var question = repository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        if (!question.getNumberId().equals(request.getNumberId())) {
            var existed = repository.existsByNumberIdAndQuestionCategory_Id(request.getNumberId(), question.getQuestionCategory().getId());
            if (existed)
                throw new Exception("question with number id " + request.getNumberId() + " already existed in category");
        }
        validate(request);
        // What can't be updated: id, category
        question.setDefaultMark(request.getDefaultMark());
        question.setShuffle(request.isShuffle());
        question.setModifiedDate(Date.from(Instant.now()));
        question.setType(request.getType());
        question.setName(request.getName());
        question.setNumberId(request.getNumberId());
        question.setGeneralFeedBack(request.getGeneralFeedback());
        question.setQuestionText(request.getQuestionText());
        question.setStatus(request.getStatus());
        if (request.getTags() != null) {
            question.setTags(String.join(", ", request.getTags()));
        } else {
            question.setTags(null);
        }
        Question finalQuestion = question;
        var questionAnswers = request.getAnswers().stream().map(a -> {
            var answer = modelMapper.map(a, QuestionAnswer.class);
            answer.setQuestion(finalQuestion);
            return answer;
        }).toList();
        question.getAnswers().clear();
        question.getAnswers().addAll(questionAnswers);
        question = repository.save(question);
        return toResponse(question);
    }

    @Override
    public QuestionResponse delete(UUID id) {
        var question = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(question);
        return toResponse(question);
    }

    private QuestionResponse toResponse(Question question) {
        var response = modelMapper.map(question, QuestionResponse.class);
        response.setTags(Arrays.stream(question.getTags().split(", ")).toList());
        return response;
    }

    private void validate(QuestionRequest request) throws Exception {
        if (request.getName() == null || request.getName().isBlank()) throw new Exception("name cannot be null");
        if (request.getQuestionText() == null || request.getQuestionText().isBlank())
            throw new Exception("Question text cannot be null");
        if (request.getDefaultMark() == null || request.getDefaultMark().isNaN())
            throw new Exception("Default mark is invalid or null");
        if (request.getTrainerId() == null) throw new Exception("Trainer ID cannot be null");
        if (request.getCategoryId() == null) throw new Exception("Category cannot be null");
    }
}
