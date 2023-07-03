package htms.service.impl;

import htms.api.domain.OverlappedSchedule;
import htms.api.request.AccountRequest;
import htms.api.request.ProfileRequest;
import htms.api.response.AccountResponse;
import htms.api.response.PageResponse;
import htms.api.response.ProfileResponse;
import htms.api.response.TraineeResponse;
import htms.common.constants.ProfileStatus;
import htms.common.constants.TraineeFileCellIndex;
import htms.common.specification.TraineeSpecification;
import htms.model.Account;
import htms.model.Class;
import htms.model.Profile;
import htms.model.Trainee;
import htms.repository.ClassRepository;
import htms.repository.TraineeRepository;
import htms.service.AccountService;
import htms.service.ProfileService;
import htms.service.ReadFileService;
import htms.service.TraineeService;
import htms.util.AccountUtil;
import htms.util.ScheduleUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final FilterBuilderService filterBuilderService;
    private final ModelMapper mapper;
    private final ReadFileService readFileService;
    private final ClassRepository classRepository;
    private AccountService accountService;
    private ProfileService profileService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public List<TraineeResponse> getTraineesByClassId(UUID classId) {
        // todo: handle exceptions
        return traineeRepository.findAllByClassId(classId)
                .orElse(List.of())
                .parallelStream()
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
                    traineeResponse.setAccountEmail(account.getEmail());
                    traineeResponse.setAccountTitle(account.getTitle());
                    return traineeResponse;
                })
                .toList();

        response.setPageStats(pg, items);
        return response;
    }

    /**
     * Save the trainee data from CSV file
     *
     * @param file {@link MultipartFile}
     * @return {@code List<TraineeResponse>}
     */
    @Override
    @Transactional(rollbackOn = {SQLException.class})
    public List<TraineeResponse> saveTraineesFromFile(MultipartFile file) {
        List<String[]> rows = readFileService.readDataFromCsv(file);
        List<AccountRequest> accountRequests = new ArrayList<>();
        List<ProfileRequest> profileRequests = new ArrayList<>();
        List<Trainee> trainees = new ArrayList<>();
        // Read all rows from the file
        // todo: generate email addresses (name+code@gmail.com. eg. binhvqse16@gmail.com)
        for (String[] row : rows) {
            String generatedEmail = AccountUtil.generateEmail(
                    row[TraineeFileCellIndex.NAME.getValue()],
                    row[TraineeFileCellIndex.CODE.getValue()]);

            accountRequests.add(AccountRequest.builder()
//                    .email(row[TraineeFileCellIndex.EMAIL.getValue()])
                    .email(generatedEmail)
                    .title("BS") // Title: BS
                    .roleId(3L) // role Trainee
                    .build());
            profileRequests.add(ProfileRequest.builder()
                    .status(ProfileStatus.PENDING)
                    .build()
            );
            // todo: assign create by id
            trainees.add(Trainee.builder()
                    .code(row[TraineeFileCellIndex.CODE.getValue()])
                    .name(row[TraineeFileCellIndex.NAME.getValue()])
                    .phone(row[TraineeFileCellIndex.PHONE_NUMBER.getValue()])
                    .createdBy(UUID.randomUUID())
                    .build());
        }
        // Create Account for each Trainee
        List<AccountResponse> accounts = accountService.createAccounts(accountRequests);
        // Create Profile for each Trainee
        List<ProfileResponse> profiles = profileService.createProfiles(profileRequests);
        // Set Profile and Account info for each Trainee
        for (Trainee trainee : trainees) {
            trainee.setAccount(Account.builder()
                    .id(accounts.get(trainees.indexOf(trainee)).getId())
                    .title(accounts.get(trainees.indexOf(trainee)).getTitle())
                    .email(accounts.get(trainees.indexOf(trainee)).getEmail())
                    .build());

            trainee.setProfile(Profile.builder()
                    .id(profiles.get(trainees.indexOf(trainee)).getId())
                    .status(profiles.get(trainees.indexOf(trainee)).getStatus())
                    .build());
        }
        // Create trainee
        traineeRepository.saveAll(trainees);
        return trainees
                .parallelStream()
                .map((element) -> mapper.map(
                        element,
                        TraineeResponse.class))
                .toList();
    }

    @Override
    public OverlappedSchedule getOverlappedScheduleOfTrainee(UUID id, String generalSchedule) {
        StringBuilder traineeGeneralSchedule = new StringBuilder();
        // Get all taking classes
        List<Class> allCurrentTakingClassesByTrainee = classRepository.findAllCurrentTakingClassesByTrainee(id)
                .orElse(List.of());
        // Get general schedules
        for (Class aClass : allCurrentTakingClassesByTrainee) {
            traineeGeneralSchedule.append(aClass.getGeneralSchedule());
        }
        // Check overlapped schedules
        return ScheduleUtil.getOverlappedSchedule(
                generalSchedule,
                traineeGeneralSchedule.toString(),
                id);
    }
}
