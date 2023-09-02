package com.example.fintech.account.controller;

import com.example.fintech.account.dto.CreateAccount;
import com.example.fintech.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * 계좌 생성 API
     */
    @PostMapping("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request) {

        var result = this.accountService.createAccount(request.getMemberId(),
                request.getProductionId(), request.getBalance());

        return CreateAccount.Response.from(result);
    }

}
