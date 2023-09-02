package com.example.fintech.account.service;

import com.example.fintech.account.domain.Account;
import com.example.fintech.account.dto.AccountDto;
import com.example.fintech.account.repository.AccountRepository;
import com.example.fintech.account.type.AccountStatus;
import com.example.fintech.member.domain.Member;
import com.example.fintech.member.repository.MemberRepository;
import com.example.fintech.production.domain.Production;
import com.example.fintech.production.repository.ProductionRepository;
import com.example.fintech.production.type.ProductionStatus;
import com.example.fintech.production.type.ProductionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ProductionRepository productionRepository;
    private final MemberRepository memberRepository;

    /**
     * 계좌 생성
     */
    @Transactional
    public AccountDto createAccount(
            String memberId, Long productionId, Long balance) {

        Account account = new Account();

        // 사용자가 존재하지 않는 경우 에러 발생
        Member member = this.memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        // 계좌 상품 종류가 존재하지 않는 상품인 경우 에러 발생
        Production production = this.productionRepository.findById(productionId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계좌 상품입니다."));

        this.validateCreateAccount(member, production);

        ProductionType type = production.getProductionCategory().getProductionType();

        // 계좌 상품이 자유적금/정기적금에 해당하는 경우, 만기일자 설정
        if (type != ProductionType.SAVINGS_ACCOUNT) {
            int months = production.getContractPeriod();

            LocalDateTime today = LocalDateTime.now();
            LocalDateTime monthsAfterLocalDate = today.plusMonths(months);

            account.setMaturityAt(monthsAfterLocalDate);
        }

        // Account entity 생성
        account.setAccountNumber(createUniqueAccountNumber());
        account.setProduction(production);
        account.setProductionType(type);
        account.setMember(member);
        account.setAccountStatus(AccountStatus.IN_USE);
        account.setBalance(balance);
        account.setRegisteredAt(LocalDateTime.now());

        this.accountRepository.save(account);

        return AccountDto.fromEntity(account);
    }

    /**
     * 계좌 생성 검증 부분
     */
    private void validateCreateAccount(Member member, Production production) {
        // 사용자 보유 계좌 확인
        if(this.accountRepository.countByMember(member) >= 5) {
            throw new RuntimeException("사용자 최대 보유 가능 계좌는 5개 입니다.");
        }
        // 계좌 상품 판매 상태 확인
        if(production.getProductionStatus() == ProductionStatus.SUSPENSION_OF_SALES) {
            throw new RuntimeException("판매 중지된 계좌 상품입니다.");
        }
    }

    /**
     * 데이터베이스 계좌 번호 중복 검사
     */
    private String createUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = createAccountNumber();
        } while (this.accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    /**
     * 계좌 번호 생성
     */
    private String createAccountNumber() {
        Random random = new Random();
        int w = random.nextInt(900) + 100; // 3자리수 100~999
        int x = random.nextInt(9000) + 1000; // 4자리수 1000~9999
        int y = random.nextInt(9000) + 1000; // 4자리수 1000~9999
        int z = random.nextInt(90) + 10; // 2자리수 10~99

        return String.format("%d-%d-%d-%d", w, x, y, z);
    }
}