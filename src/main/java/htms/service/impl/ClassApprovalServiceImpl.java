package htms.service.impl;

import htms.api.request.ApprovalRequest;
import htms.api.response.ClassApprovalResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.model.Class;
import htms.model.ClassApproval;
import htms.model.GroupedApprovalStatus;
import htms.repository.ClassApprovalRepository;
import htms.service.ClassApprovalService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .comment(request.getComment())
                .status(status)
                .clazz(Class.builder()
                        .id(request.getId())
                        .build())
                .dueDate(request.getDueDate());
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

    @Override
    public List<ClassApproval> getLatestClassApprovals() {
        //group approvals by class_id and get latest created_date for each class_id
        var groupedMaxClassApprovals = classApprovalRepository.getLatestClassApprovalsGroupedByClazzId();
        var classIdGroup = groupedMaxClassApprovals.stream().map(GroupedApprovalStatus::getClassId).toList();
        var dateTimeGroup = groupedMaxClassApprovals.stream().map(GroupedApprovalStatus::getFilteredDate).toList();
        //filter the table get the approvals from the list above
        return classApprovalRepository.findAll().stream()
                .filter(classApproval -> classIdGroup.contains(classApproval.getClazz().getId()) && dateTimeGroup.contains(classApproval.getCreatedDate()))
                .toList();
    }
}
