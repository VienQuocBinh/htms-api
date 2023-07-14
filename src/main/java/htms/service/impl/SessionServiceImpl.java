package htms.service.impl;

import htms.api.response.SessionResponse;
import htms.repository.SessionRepository;
import htms.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SessionResponse> getSessionByModuleId(UUID moduleId) {
        return sessionRepository.findAllByModule_Id(moduleId)
                .orElse(List.of())
                .stream()
                .map((element) -> modelMapper.map(element, SessionResponse.class))
                .toList();
    }
}
