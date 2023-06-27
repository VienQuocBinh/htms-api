package htms.service.impl;

import htms.api.request.TrainerRequest;
import htms.api.response.TrainerResponse;
import htms.model.Account;
import htms.model.Trainer;
import htms.repository.TrainerRepository;
import htms.service.AccountService;
import htms.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final ModelMapper mapper;
    private final TrainerRepository trainerRepository;
    private final AccountService accountService;

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
}
