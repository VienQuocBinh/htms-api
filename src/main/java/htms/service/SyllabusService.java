package htms.service;

import htms.api.response.SyllabusResponse;

import java.util.UUID;

public interface SyllabusService {
    SyllabusResponse getSyllabusByProgramId(UUID programId);
}
