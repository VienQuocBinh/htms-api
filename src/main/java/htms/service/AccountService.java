package htms.service;

import htms.api.request.AccountRequest;
import htms.api.response.AccountResponse;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountResponse getAccountById(UUID id);

    AccountResponse createAccount(AccountRequest request);

    @Transactional
    List<AccountResponse> createAccounts(List<AccountRequest> request);
}
