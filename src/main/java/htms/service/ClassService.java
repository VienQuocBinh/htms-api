package htms.service;

import htms.api.domain.CreateClassFormData;
import htms.api.request.ApprovalRequest;
import htms.api.request.ClassRequest;
import htms.api.request.ClassUpdateRequest;
import htms.api.response.ClassApprovalResponse;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.ClassStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;
import htms.model.Class;

import java.util.List;
import java.util.UUID;

public interface ClassService {
    /**
     * Creates a new class, not published, waiting for approve the class contents
     *
     * @param request {@link ClassRequest}
     * @return {@link ClassResponse}
     */
    ClassResponse createClass(ClassRequest request);

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

    List<ClassResponse> getClassesOfTrainer(UUID trainerId);

    List<ClassResponse> getClassByProgramId(UUID programId, ClassStatus status);

    List<Class> getAllCurrentTakingClassesByTrainee(UUID id);

    ClassResponse update(ClassUpdateRequest request);
}
