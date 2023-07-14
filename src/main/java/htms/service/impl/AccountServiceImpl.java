package htms.service.impl;

import htms.api.request.AccountRequest;
import htms.api.request.LoginRequest;
import htms.api.response.AccountResponse;
import htms.api.response.LoginResponse;
import htms.model.Account;
import htms.model.Role;
import htms.repository.AccountRepository;
import htms.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;


    @Override
    public AccountResponse getAccountById(UUID id) {
        // TODO: implement exception handler
        return modelMapper.map(accountRepository.findById(id)
                .orElseThrow(), AccountResponse.class);
    }

    @Override
    public Optional<Account> getAccountOptionalByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        // todo: assign created by account
        var account = accountRepository.save(Account.builder()
                .email(request.getEmail())
                .title(request.getTitle())
                .createdBy(UUID.randomUUID())
                .role(Role.builder()
                        .id(request.getRoleId())
                        .build())
                .build());
        return modelMapper.map(account, AccountResponse.class);
    }


    @Override
    @Transactional
    public List<AccountResponse> createAccounts(List<AccountRequest> request) {
        // todo: assign created by account
        List<Account> list = new ArrayList<>();
        for (AccountRequest accountRequest : request) {
            list.add(Account.builder()
                    .email(accountRequest.getGeneratedEmail())
                    .password(accountRequest.getGeneratedPassword())
                    .title(accountRequest.getTitle())
                    .createdBy(UUID.randomUUID())
                    .role(Role.builder()
                            .id(accountRequest.getRoleId())
                            .build())
                    .build());
        }

        return accountRepository.saveAll(list)
                .parallelStream()
                .map((element) -> modelMapper.map(
                        element,
                        AccountResponse.class)).toList();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        var account = accountRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(EntityNotFoundException::new);
        return LoginResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .role(account.getRole().getName())
                .build();
    }
}
