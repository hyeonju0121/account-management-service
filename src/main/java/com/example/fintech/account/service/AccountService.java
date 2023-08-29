package com.example.fintech.account.service;

import com.example.fintech.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService {
    Random random = new Random();

    private final AccountRepository accountRepository;

    /**
     * 계좌 번호 생성
     */
    public String createAccountNumber() {
        int w = random.nextInt(900) + 100; // 3자리수 100~999
        int x = random.nextInt(9000) + 1000; // 4자리수 1000~9999
        int y = random.nextInt(9000) + 1000; // 4자리수 1000~9999
        int z = random.nextInt(90) + 10; // 2자리수 10~99

        String accountNumber = String.format("%d-%d-%d-%d", w, x, y, z);

        // DB 에 계좌번호가 존재한다면, 다시 메서드 호출해서 계좌번호 생성 반복
        // 없는 경우에는 생성된 계좌번호로 결정
        String newAccountNumber = this.accountRepository.findByAccountNumber(accountNumber)
                .map(account -> this.createAccountNumber())
                .orElse(accountNumber);

        return newAccountNumber;
    }
}
