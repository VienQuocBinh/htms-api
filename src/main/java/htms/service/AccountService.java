package htms.service;

import htms.api.request.AccountRequest;
import htms.api.response.AccountResponse;
import htms.model.Account;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
    AccountResponse getAccountById(UUID id);

    Optional<Account> getAccountOptionalByEmail(String email);

    AccountResponse createAccount(AccountRequest request);

    @Transactional
    List<AccountResponse> createAccounts(List<AccountRequest> request);
}
