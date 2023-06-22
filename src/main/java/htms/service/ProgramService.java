package htms.service;

import htms.api.request.ProgramRequest;
import htms.api.response.ProgramResponse;

import java.util.List;
import java.util.UUID;

public interface ProgramService {
    List<ProgramResponse> getPrograms();

    ProgramResponse getProgramDetails(UUID id);

    ProgramResponse createProgram(ProgramRequest request);

    List<ProgramResponse> getProgramsTheTraineeIsTaking(UUID traineeId);

    ProgramResponse getProgramContent(UUID programId, UUID trainerId);
}
