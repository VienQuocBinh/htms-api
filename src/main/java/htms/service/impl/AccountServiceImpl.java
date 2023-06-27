package htms.service.impl;

import htms.api.request.AccountRequest;
import htms.api.response.AccountResponse;
import htms.model.Account;
import htms.model.Role;
import htms.repository.AccountRepository;
import htms.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
                    .email(accountRequest.getEmail())
                    .title(accountRequest.getTitle())
                    .createdBy(UUID.randomUUID())
                    .role(Role.builder()
                            .id(accountRequest.getRoleId())
                            .build())
                    .build());
        }
        return accountRepository.saveAll(list)
                .stream()
                .map((element) -> modelMapper.map(
                        element,
                        AccountResponse.class)).toList();
    }
}
