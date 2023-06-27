package htms.service.impl;

import htms.api.request.ApprovalRequest;
import htms.api.response.ClassApprovalResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.model.Class;
import htms.model.ClassApproval;
import htms.repository.ClassApprovalRepository;
import htms.service.ClassApprovalService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassApprovalServiceImpl implements ClassApprovalService {
    private final ClassApprovalRepository classApprovalRepository;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ClassApprovalResponse create(ApprovalRequest request, ClassApprovalStatus status) {
        var currentLatestId = classApprovalRepository.getLatestId();
        var classApproval = ClassApproval.builder()
                .status(status)
                .clazz(Class.builder()
                        .id(request.getId())
                        .build());
        // if the data table is already has data, then create with id = latestId + 1
        if (currentLatestId.isPresent()) {
            Long currentId = (Long) entityManager.createNativeQuery("SELECT setval('class_approval_id_seq', " + currentLatestId.get() + ", true);")
                    .getSingleResult();
            classApproval.id(currentId + 1);
        }
        return modelMapper.map(
                classApprovalRepository.save(classApproval.build()),
                ClassApprovalResponse.class);
    }
}
