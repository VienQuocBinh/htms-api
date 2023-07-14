package htms.controller;

import htms.api.request.ExternalResourceRequest;
import htms.api.response.ExternalResourceResponse;
import htms.service.ExternalResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic/external-resource")
public class ExternalResourceController {
    private final ExternalResourceService service;

    @GetMapping("/{resource_id}")
    public ResponseEntity<ExternalResourceResponse> getExternalResourceDetail(@PathVariable UUID resource_id) {
        return ResponseEntity.ok(service.getResourceDetail(resource_id));
    }

    @PostMapping
    public ResponseEntity<ExternalResourceResponse> createNewExternalResource(@RequestBody ExternalResourceRequest request) {
        return new ResponseEntity<>(service.createResource(request), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ExternalResourceResponse> updateExternalResource(@RequestBody ExternalResourceRequest request) {
        return new ResponseEntity<>(service.updateResource(request), HttpStatus.OK);
    }

    @DeleteMapping("/{resource_id}")
    public ResponseEntity<ExternalResourceResponse> deleteResource(@PathVariable UUID resource_id) {
        return ResponseEntity.ok(service.deleteResource(resource_id));
    }

}
