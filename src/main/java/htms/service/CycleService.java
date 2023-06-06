package htms.service;

import htms.api.request.CycleRequest;
import htms.api.response.CycleResponse;

import java.util.List;

public interface CycleService {
    List<CycleResponse> getCycles();

    CycleResponse createCycle(CycleRequest request);
}
