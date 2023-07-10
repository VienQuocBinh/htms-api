package htms.service.impl;

import htms.api.request.QuizRequest;
import htms.api.response.QuizResponse;
import htms.common.constants.TopicSlotType;
import htms.model.Quiz;
import htms.model.TopicSlot;
import htms.repository.*;
import htms.service.QuizService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository repository;
    private final TopicRepository topicRepository;
    private final TopicSlotRepository topicSlotRepository;
    private final TrainerRepository trainerRepository;
    private final QuestionCategoryRepository questionCategoryRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper mapper;

    @Override
    public QuizResponse getQuizDetail(UUID id) {
        var quiz = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return toQuizResponse(quiz);
    }

    @Override
    public QuizResponse createQuiz(QuizRequest request) throws Exception {
        validate(request);
        var topic = topicRepository.findById(request.getTopicId()).orElseThrow(EntityNotFoundException::new);
        var trainer = trainerRepository.findById(request.getTrainerId()).orElseThrow(EntityNotFoundException::new);
        var newQuiz = mapper.map(request, Quiz.class);
        newQuiz.setTags(String.join(", ", request.getTags()));
        newQuiz.setTrainer(trainer);
        Integer lastPosition = topic.getSlots().isEmpty() ? Integer.valueOf(0) : Collections.max(topic.getSlots(), Comparator.comparing(TopicSlot::getPosition)).getPosition();
        var newSlot = TopicSlot.builder()
                .topic(topic)
                .quiz(newQuiz)
                .position(++lastPosition)
                .type(TopicSlotType.QUIZ)
                .build();
        topic.getSlots().add(newSlot);
        topic = topicRepository.save(topic);
        Integer finalLastPosition = lastPosition;
        newQuiz = topic.getSlots().stream().filter(topicSlot -> topicSlot.getPosition().equals(finalLastPosition)).findFirst().get().getQuiz();
        return toQuizResponse(newQuiz);
    }



    @Override
    public QuizResponse updateQuiz(QuizRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var quiz = repository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
//        PropertyUtils.copyProperties(quiz, request);
        quiz.setName(request.getName());
        quiz.setDescription(request.getDescription());
        quiz.setOpenTime(request.getOpenTime());
        quiz.setCloseTime(request.getCloseTime());
        quiz.setTimeLimit(request.getTimeLimit());
        quiz.setUnit(request.getUnit());
        quiz.setGradeToPass(request.getGradeToPass());
        quiz.setAllowedAttempt(request.getAllowedAttempt());
        quiz.setGradingMethod(request.getGradingMethod());
        quiz.setShuffle(request.getShuffle());
        quiz.setReviewable(request.getReviewable());
        quiz.setPassword(request.getPassword());
        quiz.setMaxGrade(request.getMaxGrade());
        if(request.getTags() != null && !request.getTags().isEmpty()) {
            quiz.setTags(String.join(", ", request.getTags()));
        } else {
            quiz.setTags("");
        }
        quiz = repository.save(quiz);
        return toQuizResponse(quiz);
    }

    private QuizResponse toQuizResponse(Quiz quiz) {
        var res = mapper.map(quiz, QuizResponse.class);
        res.setHavePassword(quiz.getPassword() != null && !quiz.getPassword().isEmpty());
        return res;
    }

    @Override
    public QuizResponse deleteQuiz(UUID id) {
        var slot = topicSlotRepository.findByQuiz_Id(id).orElseThrow(EntityNotFoundException::new);
        var quiz = slot.getQuiz();
        topicSlotRepository.delete(slot);
        return toQuizResponse(quiz);
    }

    private void validate(QuizRequest request) throws Exception {
        if(request.getOpenTime().after(request.getCloseTime())) {
            throw new Exception("Open time must be for close time");
        }
        if(request.getGradeToPass() != null && request.getMaxGrade().compareTo(request.getGradeToPass()) < 0) {
            throw new Exception("Grade to pass must lower than max grade");
        }
    }

}
