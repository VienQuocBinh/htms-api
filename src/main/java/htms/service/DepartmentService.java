package htms.service;

import htms.api.request.DepartmentRequest;
import htms.api.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getDepartments();

    DepartmentResponse createDepartment(DepartmentRequest request);
}
