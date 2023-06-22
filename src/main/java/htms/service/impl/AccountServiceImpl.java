package htms.service.impl;

import htms.api.request.AccountRequest;
import htms.api.response.AccountResponse;
import htms.model.Account;
import htms.model.Role;
import htms.repository.AccountRepository;
import htms.repository.RoleRepository;
import htms.service.AccountService;
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
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public AccountResponse getAccountById(UUID id) {
        // TODO: implement exception handler
        return modelMapper.map(accountRepository.findById(id)
                .orElseThrow(), AccountResponse.class);
    }

    @Override
    public List<AccountResponse> createAccount(AccountRequest request) {
        Role role = Role.builder().name("admin").build();
        roleRepository.save(role);
        Account account1 = Account.builder()
                .email("ag@gmail.com")
                .title("DD")
                .createdBy(UUID.randomUUID())
                .role(role)
                .build();
        Account account2 = Account.builder()
                .email("123213@gmail.com")
                .title("DDsdfef")
                .createdBy(UUID.randomUUID())
                .role(role)
                .build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accountRepository.saveAll(accounts);

        return accounts.stream()
                .map((element) -> modelMapper.map(element, AccountResponse.class))
                .toList();
    }
}
