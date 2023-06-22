package htms.controller;

import htms.api.domain.CreateClassFormData;
import htms.api.domain.FilterCondition;
import htms.api.request.ApprovalRequest;
import htms.api.request.ClassRequest;
import htms.api.response.*;
import htms.common.constants.ClassApprovalStatus;
import htms.common.constants.EnrollmentStatus;
import htms.common.constants.SortBy;
import htms.common.constants.SortDirection;
import htms.model.Class;
import htms.repository.ClassRepository;
import htms.service.ClassService;
import htms.service.EnrollmentService;
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
    private final EnrollmentService enrollmentService;

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
    public ResponseEntity<ClassResponse> createClass(@RequestBody ClassRequest request) {
        // todo: generate schedules
        return ResponseEntity.ok(classService.createClass(request));
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
        Page<Class> pg = classRepository.findAll(specification, pageable);
        response.setPageStats(pg,
                pg.getContent()
                        .stream()
                        .map((element) -> modelMapper.map(
                                element,
                                ClassResponse.class)).toList());
        return ResponseEntity.ok(response);
    }

    //    @GetMapping("/page/approval")
//    public ResponseEntity<PageResponse<ClassApprovalResponse>> getSearchAppCriteriaPage(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size,
//            @RequestParam(value = "filterOr", required = false) String filterOr,
//            @RequestParam(value = "filterAnd", required = false) String filterAnd,
//            @RequestParam(value = "orders", required = false) String orders
//    ) {
//        PageResponse<ClassApprovalResponse> response = new PageResponse<>();
//        Pageable pageable = filterBuilderService.getPageable(size, page, orders);
//
//        GenericFilterCriteriaBuilder<ClassApproval> filterCriteriaBuilder = new GenericFilterCriteriaBuilder<>();
//        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
//        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);
//
//        Specification<ClassApproval> specification = filterCriteriaBuilder.addCondition(andConditions, orConditions);
//        Page<ClassApproval> pg = classApprovalRepository.findAll(specification, pageable);
//        response.setPageStats(pg,
//                pg.getContent()
//                        .stream()
//                        .map((element) -> modelMapper.map(
//                                element,
//                                ClassApprovalResponse.class)).toList());
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/approve")
    public ResponseEntity<ClassApprovalResponse> approveClassRequest(@Valid @RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(classService.makeApproval(request, ClassApprovalStatus.APPROVE));
    }

    @PostMapping("/reject")
    public ResponseEntity<ClassApprovalResponse> rejectClassRequest(@Valid @RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(classService.makeApproval(request, ClassApprovalStatus.REJECT));
    }

    @GetMapping("/form")
    public ResponseEntity<CreateClassFormData> initCreateClassFormData() {
        return ResponseEntity.ok(classService.initCreateClassFormData());
    }

    @GetMapping("/class/{id}/enrollment")
    public ResponseEntity<List<EnrollmentResponse>> getClassEnrollments(@PathVariable UUID id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentByClassIdAndStatus(id, EnrollmentStatus.PENDING));
    }
}
