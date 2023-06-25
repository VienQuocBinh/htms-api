package htms.service.impl;

import htms.api.response.PageResponse;
import htms.api.response.TraineeResponse;
import htms.common.specification.TraineeSpecification;
import htms.model.Trainee;
import htms.repository.TraineeRepository;
import htms.service.AccountService;
import htms.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final FilterBuilderService filterBuilderService;
    private final ModelMapper mapper;
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public List<TraineeResponse> getTraineesByClassId(UUID classId) {
        // todo: handle exceptions
        var list = traineeRepository.findAllByClassId(classId).orElseThrow();
        return list.stream()
                .map(trainees -> mapper.map(trainees, TraineeResponse.class))
                .toList();
    }

    @Override
    public PageResponse<TraineeResponse> getTraineesPage(int page, int size, String q, String title, UUID departmentId, String orders) {
        Pageable pageable = filterBuilderService.getPageable(size, page, orders);
        /* Older versions
        GenericFilterCriteriaBuilder<Trainee> filterCriteriaBuilder = new GenericFilterCriteriaBuilder<>();
        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(departmentId);
        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(title);
        Specification<Trainee> specification = filterCriteriaBuilder.addCondition(andConditions, orConditions);
*/
        Specification<Trainee> specification = Specification.where(null);
        if (q != null) {
            specification = TraineeSpecification.hasNameLike(q)
                    .or(TraineeSpecification.hasCodeLike(q));
        }
        if (departmentId != null) {
            specification = specification.and(TraineeSpecification.hasDepartmentEqual(departmentId));
        }
        if (title != null) {
            specification = specification.and(TraineeSpecification.hasTitleEqual(title));
        }

        Page<Trainee> pg = traineeRepository.findAll(specification, pageable);

        PageResponse<TraineeResponse> response = new PageResponse<>();
        var items = pg.getContent().stream().map((element) ->
                {
                    TraineeResponse traineeResponse = mapper.map(element, TraineeResponse.class);
                    var account = accountService.getAccountById(element.getAccount().getId());
                    traineeResponse.setEmail(account.getEmail());
                    traineeResponse.setTitle(account.getTitle());
                    return traineeResponse;
                })
                .toList();

        response.setPageStats(pg, items);
        return response;
    }

    @Override
    public TraineeResponse getTrainee(UUID traineeId) {
        // todo: handle exceptions
        return mapper.map(traineeRepository.findById(traineeId)
                        .orElseThrow(EntityNotFoundException::new),
                TraineeResponse.class);
    }
}
