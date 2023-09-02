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
import com.example.fintech.production.type.ProductionStatus;
import com.example.fintech.production.type.ProductionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        // given
        Member member = Member.builder()
                .memberId("testMemberId")
                .build();

        ProductionCategory productionCategory = ProductionCategory.builder()
                .id(1L)
                .productionType(ProductionType.SAVINGS_ACCOUNT)
                .build();

        Production production = Production.builder()
                .id(1L)
                .productionCategory(productionCategory)
                .build();

        given(memberRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(member));
        given(productionRepository.findById(anyLong()))
                .willReturn(Optional.of(production));


        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountDto accountDto = accountService.createAccount("testMemberId", 1L, 5000L);

        // then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals("testMemberId", accountDto.getMemberId());
        assertEquals(1L, accountDto.getProduction());
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 경우 - 계좌 생성 실패")
    void createAccountFailed_UserNotFound() {
        //given
        given(memberRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        //when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> accountService.createAccount("testMemberId", 1L, 5000L))
                .withMessageContaining("사용자가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("사용자 보유 계좌 5개 이상인 경우 - 계좌 생성 실패")
    void createAccountFailed_maxAccountIs5() {
        //given
        Member member = Member.builder()
                .id(1L)
                .memberId("testMemberId").build();
        given(memberRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(member));

        given(productionRepository.findById(anyLong()))
                .willReturn(Optional.of(Production.builder()
                        .id(1L).build()));

        given(accountRepository.countByMember(any()))
                .willReturn(5);

        //when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> accountService.createAccount("testMemberId", 1L, 5000L))
                .withMessageContaining("사용자 최대 보유 가능 계좌는 5개 입니다.");
    }

    @Test
    @DisplayName("계좌 상품 종류가 존재하지 않는 경우 - 계좌 생성 실패")
    void createAccountFailed_ProductionNotFound() {
        //given
        Member member = Member.builder()
                .id(1L)
                .memberId("testMemberId").build();
        given(memberRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(member));

        given(productionRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        //when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> accountService.createAccount("testMemberId", 1L, 5000L))
                .withMessageContaining("존재하지 않는 계좌 상품입니다.");
    }

    @Test
    @DisplayName("해당 계좌 상품이 판매 중지된 경우 - 계좌 생성 실패")
    void createAccountFailed_ProductionAlreadySuspensionOfSales() {
        //given
        Member member = Member.builder()
                .id(1L)
                .memberId("testMemberId").build();
        given(memberRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(member));

        given(productionRepository.findById(anyLong()))
                .willReturn(Optional.of(Production.builder()
                        .id(1L)
                        .productionStatus(ProductionStatus.SUSPENSION_OF_SALES)
                        .build()));

        //when
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> accountService.createAccount("testMemberId", 1L, 5000L))
                .withMessageContaining("판매 중지된 계좌 상품입니다.");
    }
}
