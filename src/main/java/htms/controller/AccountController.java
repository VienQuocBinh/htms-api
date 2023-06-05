package htms.controller;

import htms.api.request.AccountRequest;
import htms.api.response.AccountResponse;
import htms.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<List<AccountResponse>> createAccount(@RequestBody @Valid AccountRequest request) {
        return ResponseEntity.status(201).body(accountService.createAccount(request));
    }
}
