package htms.service.impl;

import htms.api.request.ExternalResourceRequest;
import htms.api.response.ExternalResourceResponse;
import htms.common.constants.TopicSlotType;
import htms.model.ExternalResource;
import htms.model.TopicSlot;
import htms.repository.ExternalResourceRepository;
import htms.repository.TopicRepository;
import htms.repository.TopicSlotRepository;
import htms.service.ExternalResourceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExternalResourceServiceImpl implements ExternalResourceService {

    private final ExternalResourceRepository repository;
    private final TopicRepository topicRepository;
    private final TopicSlotRepository topicSlotRepository;
    private final ModelMapper modelMapper;
    @Override
    public ExternalResourceResponse getResourceDetail(UUID resourceId) {
        var resource = repository.findById(resourceId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(resource, ExternalResourceResponse.class);
    }

    @Override
    public ExternalResourceResponse createResource(ExternalResourceRequest request) {
        var topic = topicRepository.findById(request.getTopicId()).orElseThrow(EntityNotFoundException::new);
        var newTopicBuilder = TopicSlot.builder();
        newTopicBuilder.type(TopicSlotType.RESOURCE).topic(topic);
        var resource = modelMapper.map(request, ExternalResource.class);
        newTopicBuilder.externalResource(resource);
        Integer lastPosition = topic.getSlots().isEmpty() ? Integer.valueOf(0) : Collections.max(topic.getSlots(), Comparator.comparing(TopicSlot::getPosition)).getPosition();
        newTopicBuilder.position(++lastPosition);
        topic.getSlots().add(newTopicBuilder.build());
        topic = topicRepository.save(topic);
        Integer finalLastPosition = lastPosition;
        var response = topic.getSlots().stream().filter(topicSlot -> topicSlot.getPosition().equals(finalLastPosition)).findFirst().get();
        return modelMapper.map(response.getExternalResource(), ExternalResourceResponse.class);
    }

    @Override
    public ExternalResourceResponse updateResource(ExternalResourceRequest request) {
        var resource = repository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);
        resource.setName(request.getName());
        resource.setDescription(request.getDescription());
        resource.setExternalUrl(request.getExternalUrl());
        resource = repository.save(resource);
        return modelMapper.map(resource, ExternalResourceResponse.class);
    }

    @Override
    public ExternalResourceResponse deleteResource(UUID id) {
        var slot = topicSlotRepository.findByExternalResource_Id(id).orElseThrow(EntityNotFoundException::new);
        var resource =slot.getExternalResource();
        topicSlotRepository.delete(slot);
        return modelMapper.map(resource, ExternalResourceResponse.class);
    }
}
