package com.example.fintech.account.controller;

import com.example.fintech.account.dto.CreateAccount;
import com.example.fintech.account.dto.DeleteAccount;
import com.example.fintech.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    /**
     * 계좌 해지 API
     */
    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request) {

        var result = this.accountService.deleteAccount(
                request.getMemberId(), request.getAccountNumber(),
                request.getSimplePassword(), request.getTransferAccountNumber());

        return DeleteAccount.Response.from(result);
    }
}
