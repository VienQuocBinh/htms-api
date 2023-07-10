package htms.service.impl;

import htms.api.request.TopicRequest;
import htms.api.response.TopicResponse;
import htms.model.Topic;
import htms.model.TopicSlot;
import htms.repository.ProgramContentRepository;
import htms.repository.TopicRepository;
import htms.service.TopicService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final ProgramContentRepository programContentRepository;
    private final TopicRepository topicRepository;
    private final ModelMapper mapper;

    @Override
    public List<TopicResponse> getProgramTopics(UUID programId, UUID trainerId) {
        var content = programContentRepository.findById_Program_IdAndId_Trainer_Id(programId, trainerId).orElseThrow(EntityNotFoundException::new);
        var topics = content.getTopics();
        for (Topic topic : topics) {
            topic.getSlots().sort(Comparator.comparing(TopicSlot::getPosition));
        }
        return topics.stream().map(topic -> mapper.map(topic, TopicResponse.class)).toList();
    }

    @Override
    public TopicResponse createNewTopic(TopicRequest request) throws Exception {
        if(request.getTrainerId() == null || request.getProgramId() == null) throw new Exception("trainer id or program id is null");
        var content = programContentRepository.findById_Program_IdAndId_Trainer_Id(request.getProgramId(), request.getTrainerId()).orElseThrow(EntityNotFoundException::new);
        var topic = mapper.map(request, Topic.class);
        topic.setProgramContent(content);
        topic = topicRepository.save(topic);
        return mapper.map(topic, TopicResponse.class);
    }

    @Override
    public TopicResponse updateTopic(TopicRequest request) {
        var topic = topicRepository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        topic.setName(request.getName());
        topic.setDescription(request.getDescription());
        //reorder
        for (TopicSlot slot : topic.getSlots()) {
            request.getSlots().stream().filter(topicSlotRequest -> topicSlotRequest.getId() == slot.getId()).findFirst().ifPresent(s -> slot.setPosition(s.getSlot()));
        }
        topic = topicRepository.save(topic);
        return mapper.map(topic, TopicResponse.class);
    }

    @Override
    public TopicResponse delete(UUID topicId) throws Exception {
        var topic = topicRepository.findById(topicId).orElseThrow(EntityNotFoundException::new);
        if(!topic.getSlots().isEmpty()) {
            throw new Exception("Topic has more than one resources/activities. Please remove before delete topic");
        }
        topicRepository.delete(topic);
        return mapper.map(topic, TopicResponse.class);
    }
}
