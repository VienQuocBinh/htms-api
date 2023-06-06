package htms.service;

import htms.api.request.ProgramPerCycleRequest;
import htms.api.response.ProgramPerCycleResponse;

import java.util.List;

public interface ProgramPerCycleService {
    List<ProgramPerCycleResponse> getList();

    ProgramPerCycleResponse create(ProgramPerCycleRequest request);
}
