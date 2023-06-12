package htms.service;

import htms.api.request.ProgramPerCycleRequest;
import htms.api.response.ProgramPerClassResponse;

import java.util.List;

public interface ProgramPerCycleService {
    List<ProgramPerClassResponse> getList();

    ProgramPerClassResponse create(ProgramPerCycleRequest request);
}
