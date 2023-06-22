package htms.service;

import htms.api.request.AccountRequest;
import htms.api.response.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountResponse getAccountById(UUID id);

    List<AccountResponse> createAccount(AccountRequest request);
}
