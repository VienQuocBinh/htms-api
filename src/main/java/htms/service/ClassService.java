package htms.service;

import htms.api.domain.CreateClassFormData;
import htms.api.request.ApprovalRequest;
import htms.api.request.ClassRequest;
import htms.api.response.ClassApprovalResponse;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ClassService {
    CompletableFuture<ClassResponse> createClass(ClassRequest request);

    List<ClassResponse> getClasses();

    /**
     * Get class details including list of APPROVED trainees
     *
     * @param id {@link String}
     * @return {@link List}
     */
    ClassResponse getClassDetail(UUID id);

    List<ClassesApprovalResponse> searchClasses(String q, ClassApprovalStatus status, SortBy sortBy, SortDirection direction);

    ClassApprovalResponse makeApproval(ApprovalRequest request, ClassApprovalStatus status);

    CreateClassFormData initCreateClassFormData();
}
