package htms.service;

import htms.api.request.ApprovalRequest;
import htms.api.response.ClassApprovalResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.model.ClassApproval;

import java.util.List;

public interface ClassApprovalService {
    ClassApprovalResponse create(ApprovalRequest request, ClassApprovalStatus status);

    List<ClassApproval> getLatestClassApprovals();
}
