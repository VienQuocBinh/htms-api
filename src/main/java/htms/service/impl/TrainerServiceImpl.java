package htms.service.impl;

import htms.api.domain.OverlappedSchedule;
import htms.api.request.TrainerRequest;
import htms.api.response.TrainerResponse;
import htms.model.Account;
import htms.model.Trainer;
import htms.repository.TrainerRepository;
import htms.service.AccountService;
import htms.service.ClassService;
import htms.service.TrainerService;
import htms.util.ScheduleUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final ModelMapper mapper;
    private final TrainerRepository trainerRepository;
    private AccountService accountService;
    private ClassService classService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private void setClassService(@Lazy ClassService classService) {
        this.classService = classService;
    }

    @Override
    public TrainerResponse createTrainer(TrainerRequest request) {
        // TODO: Auditing the created by. Currently, set created by is same as reference account
        var account = accountService.getAccountById(request.getAccountId());
        var trainer = Trainer.builder()
                .name(request.getName())
                .birthdate(request.getBirthdate())
                .phone(request.getPhone())
                .account(Account.builder()
                        .id(account.getId())
                        .build())
                .createdBy(account.getId())
                .build();
        trainerRepository.save(trainer);
        return mapper.map(trainer, TrainerResponse.class);
    }

    @Override
    public List<TrainerResponse> getTrainers() {
        return trainerRepository.findAll()
                .stream()
                .map(element -> mapper.map(element, TrainerResponse.class))
                .toList();
    }

    @Override
    public TrainerResponse getTrainer(UUID id) {
        return mapper.map(trainerRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new), TrainerResponse.class);
    }

    @Override
    public OverlappedSchedule getOverlappedScheduleOfTrainer(UUID id, String generalSchedule) {
        StringBuilder trainerGeneralSchedule = new StringBuilder();
        var classes = classService.getClassesOfTrainer(id);
        classes.forEach(c -> trainerGeneralSchedule.append(c.getGeneralSchedule()));
        return ScheduleUtil.getOverlappedSchedule(
                generalSchedule,
                trainerGeneralSchedule.toString(),
                id);
    }
}
