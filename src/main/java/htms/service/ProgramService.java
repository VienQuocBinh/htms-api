package htms.service;

import htms.api.request.ProgramRequest;
import htms.api.response.ProgramResponse;

import java.util.List;

public interface ProgramService {
    List<ProgramResponse> getPrograms();

    ProgramResponse createProgram(ProgramRequest request);
}
