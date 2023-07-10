package htms.service;

import htms.api.request.TopicRequest;
import htms.api.response.TopicResponse;

import java.util.List;
import java.util.UUID;

public interface TopicService {
    List<TopicResponse> getProgramTopics(UUID programId, UUID trainerId);

    TopicResponse createNewTopic(TopicRequest request) throws Exception;

    TopicResponse updateTopic(TopicRequest request);

    TopicResponse delete(UUID topicId) throws Exception;
}
