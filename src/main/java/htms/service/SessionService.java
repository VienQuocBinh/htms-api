package htms.service;

import htms.api.response.SessionResponse;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    List<SessionResponse> getSessionByModuleId(UUID moduleId);
}
