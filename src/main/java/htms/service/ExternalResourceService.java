package htms.service;


import htms.api.request.ExternalResourceRequest;
import htms.api.response.ExternalResourceResponse;

import java.util.UUID;

public interface ExternalResourceService {
    ExternalResourceResponse getResourceDetail(UUID uuid);

    ExternalResourceResponse createResource(ExternalResourceRequest request);

    ExternalResourceResponse updateResource(ExternalResourceRequest request);

    ExternalResourceResponse deleteResource(UUID id);
}
