package htms.service.impl;

import htms.api.response.ModuleResponse;
import htms.api.response.SyllabusResponse;
import htms.common.exception.EntityNotFoundException;
import htms.model.Syllabus;
import htms.repository.SyllabusRepository;
import htms.service.SessionService;
import htms.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SyllabusServiceImpl implements SyllabusService {
    private final ModelMapper modelMapper;
    private final SyllabusRepository syllabusRepository;
    private final SessionService sessionService;

    @Override
    public SyllabusResponse getSyllabusByProgramId(UUID programId) {
        SyllabusResponse map = modelMapper.map(syllabusRepository.findByProgram_Id(programId)
                        .orElseThrow(() -> new EntityNotFoundException(Syllabus.class, "program id", programId)),
                SyllabusResponse.class);
        // Set sessions for each module
        for (ModuleResponse module : map.getModules()) {
            module.setSessions(sessionService.getSessionByModuleId(module.getId()));
        }
        return map;
    }
}
