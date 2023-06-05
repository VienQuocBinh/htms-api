package htms.service;

import htms.api.request.AccountRequest;
import htms.api.response.AccountResponse;
import htms.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account getAccountById(UUID id);

    List<AccountResponse> createAccount(AccountRequest request);
}
