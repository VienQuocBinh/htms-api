package htms.service;

import htms.api.request.ApprovalRequest;
import htms.api.response.ClassApprovalResponse;
import htms.common.constants.ClassApprovalStatus;

public interface ClassApprovalService {
    ClassApprovalResponse create(ApprovalRequest request, ClassApprovalStatus status);
}
