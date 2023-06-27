package htms.service.impl;

import htms.api.request.ActivityRequest;
import htms.api.response.ActivityResponse;
import htms.model.Activity;
import htms.model.ProgramContent;
import htms.model.Topic;
import htms.repository.ActivityRepository;
import htms.repository.ProgramContentRepository;
import htms.repository.TopicRepository;
import htms.service.ActivityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final TopicRepository topicRepository;
    private final ActivityRepository activityRepository;
    private final ProgramContentRepository programContentRepository;
    private final ModelMapper modelMapper;
    @Override
    public ActivityResponse saveNewActivity(ActivityRequest request) {
        ProgramContent programContent = programContentRepository.findById_Program_IdAndId_Trainer_Id(request.getProgramId(), request.getTrainerId()).orElseThrow(EntityNotFoundException::new);
        var topic = programContent.getTopics().stream().filter(t -> t.getName().equals(request.getTopicName())).findFirst().orElse(topicRepository.save(Topic.builder().name(request.getTopicName()).programContent(programContent).build()));
        var newActivity = Activity.builder()
                .name(request.getName())
                .topic(topic)
                .description(request.getDescription())
                .materialLink(request.getMaterialLink()).build();
        newActivity = activityRepository.save(newActivity);
        return modelMapper.map(newActivity, ActivityResponse.class);
    }
}
