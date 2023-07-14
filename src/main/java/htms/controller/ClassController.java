package htms.controller;

import htms.api.domain.CreateClassFormData;
import htms.api.domain.FilterCondition;
import htms.api.request.ApprovalRequest;
import htms.api.request.ClassRequest;
import htms.api.response.ClassApprovalResponse;
import htms.api.response.ClassResponse;
import htms.api.response.ClassesApprovalResponse;
import htms.api.response.PageResponse;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.ClassStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;
import htms.model.Class;
import htms.repository.ClassRepository;
import htms.service.ClassService;
import htms.service.impl.FilterBuilderService;
import htms.service.impl.GenericFilterCriteriaBuilder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
public class ClassController {
    private final ClassService classService;
    private final FilterBuilderService filterBuilderService;
    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ClassResponse>> getClasses() {
        return ResponseEntity.ok(classService.getClasses());
    }

    @GetMapping("/{id}")
    @Operation(description = "Returns the class detail including list of trainees who have the same enrollment status with the class approval")
    public ResponseEntity<ClassResponse> getClassDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(classService.getClassDetail(id));
    }

    @PostMapping
    @Operation(summary = "Create a new class")
    public ResponseEntity<ClassResponse> createClass(@RequestBody @Valid ClassRequest request) {
        return ResponseEntity.status(201).body(classService.createClass(request));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClassesApprovalResponse>> searchClasses(
            @Valid @RequestParam(required = false) Optional<String> q,
            @Valid @RequestParam(required = false, defaultValue = "PENDING") ClassApprovalStatus status,
            @Valid @RequestParam(required = false, defaultValue = "CREATED_DATE") SortBy sortBy,
            @Valid @RequestParam(required = false, defaultValue = "DESC") SortDirection direction
    ) {
        return ResponseEntity.ok(classService.searchClasses(q.orElse(""), status, sortBy, direction));
    }

    @GetMapping("/page")
    @Operation(summary = "Get a page of classes for the given conditions")
    public ResponseEntity<PageResponse<ClassResponse>> getSearchCriteriaPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filterOr", required = false) String filterOr,
            @RequestParam(value = "filterAnd", required = false) String filterAnd,
            @RequestParam(value = "orders", required = false) String orders
    ) {
        PageResponse<ClassResponse> response = new PageResponse<>();
        Pageable pageable = filterBuilderService.getPageable(size, page, orders);

        GenericFilterCriteriaBuilder<Class> filterCriteriaBuilder = new GenericFilterCriteriaBuilder<>();
        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);

        Specification<Class> specification = filterCriteriaBuilder.addCondition(andConditions, orConditions);

        // pass the status to the specification
//        Specification<Class> latestClassSpecification = ClassSpecification
//                .classesWithLatestApproval(ClassApprovalStatus.PENDING);
//        Page<Class> pg = classRepository.findAll(specification.and(latestClassSpecification), pageable);
        Page<Class> pg = classRepository.findAll(specification, pageable);
        var items = pg.getContent()
                .stream()
                .map((element) -> modelMapper.map(
                        element,
                        ClassResponse.class)).toList();

        response.setPageStats(pg, items);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve-for-publishing")
    @Operation(summary = "Approve in first time about the class content")
    public ResponseEntity<ClassApprovalResponse> approvePublishClass(@RequestBody @Valid ApprovalRequest request) {
        return ResponseEntity.ok(classService.makeApproval(request, ClassApprovalStatus.APPROVE_FOR_PUBLISHING));
    }

    @PostMapping("/reject-for-publishing")
    @Operation(summary = "Reject in first time about the class content")
    public ResponseEntity<ClassApprovalResponse> rejectPublishClass(@RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(classService.makeApproval(request, ClassApprovalStatus.REJECT_FOR_PUBLISHING));
    }

    @PostMapping("/approve-for-opening")
    @Operation(summary = "Approve in second time to open the class")
    public ResponseEntity<ClassApprovalResponse> approveClassRequest(@Valid @RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(classService.makeApproval(request, ClassApprovalStatus.APPROVE_FOR_OPENING));
    }

    @PostMapping("/reject-for-opening")
    @Operation(summary = "Reject in second time to close the class")
    public ResponseEntity<ClassApprovalResponse> rejectClassRequest(@Valid @RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(classService.makeApproval(request, ClassApprovalStatus.REJECT_FOR_OPENING));
    }

    @GetMapping("/form")
    @Operation(summary = "Init the form data for creating a new class")
    public ResponseEntity<CreateClassFormData> initCreateClassFormData() {
        return ResponseEntity.ok(classService.initCreateClassFormData());
    }


    @GetMapping("/program/{id}")
    @Operation(summary = "Get pending classes related to a program to enrol")
    public ResponseEntity<List<ClassResponse>> getClassByProgramId(
            @PathVariable UUID id,
            @Valid @RequestParam(required = false, defaultValue = "PENDING") ClassStatus status) {
        return ResponseEntity.ok(classService.getClassByProgramId(id, status));
    }
}
