package htms.service;

import htms.api.request.CycleRequest;
import htms.api.response.CycleResponse;

import java.util.List;
import java.util.UUID;

public interface CycleService {
    List<CycleResponse> getCycles();

    CycleResponse getCycleDetails(UUID id);

    CycleResponse createCycle(CycleRequest request);
}
