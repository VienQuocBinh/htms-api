package htms.service;

import htms.api.request.ProgramPerClassRequest;
import htms.api.response.ProgramPerClassResponse;
import htms.model.ProgramPerClass;

import java.util.List;

public interface ProgramPerClassService {
    List<ProgramPerClassResponse> getList();

    ProgramPerClass create(ProgramPerClassRequest request);
}
