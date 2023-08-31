package com.example.fintech.service;

import com.example.fintech.account.domain.Account;
import com.example.fintech.account.dto.AccountDto;
import com.example.fintech.account.repository.AccountRepository;
import com.example.fintech.account.service.AccountService;
import com.example.fintech.member.domain.Member;
import com.example.fintech.member.repository.MemberRepository;
import com.example.fintech.production.domain.Production;
import com.example.fintech.production.domain.ProductionCategory;
import com.example.fintech.production.repository.ProductionRepository;
import com.example.fintech.production.type.ProductionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductionRepository productionRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("계좌 개설 서비스 테스트")
    void createAccountSuccess() {
        //given
        String memberId = "testMemberId";
        Long productionId = 1L;
        Long balance = 5000L;

        Member member = new Member();
        member.setId(1L);

        Production production = new Production();
        production.setId(1L);

        ProductionCategory productionCategory = new ProductionCategory();
        productionCategory.setProductionType(ProductionType.FREE_SAVINGS);
        production.setProductionCategory(productionCategory);

        given(memberRepository.findByMemberId(anyString())).willReturn(Optional.of(member));
        given(productionRepository.findById(anyLong())).willReturn(Optional.of(production));
        given(accountRepository.findByMember_id(anyLong())).willReturn(List.of(new Account(), new Account(), new Account(), new Account()));

        // when
        AccountDto result = accountService.createAccount(memberId, productionId, balance);

        // then
        verify(accountRepository).save(any());
    }

    @Test
    @DisplayName("사용자 보유 계좌 5개 이상일 경우 예외 발생 테스트")
    void createAccountWithThrowsException() {
        //given
        String memberId = "testMemberId";
        Long productionId = 1L;
        Long balance = 5000L;

        Member member = new Member();
        member.setId(1L);

        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Account account = new Account();
            accounts.add(account);
        }

        when(memberRepository.findByMemberId(anyString())).thenReturn(Optional.of(member));
        when(accountRepository.findByMember_id(anyLong())).thenReturn(accounts);

        //when, then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> accountService.createAccount(memberId, productionId, balance))
                .withMessageContaining("계좌를 개설할 수 없습니다. 최대 보유 가능 계좌 수는 5개 입니다.");
    }
}
