package com.example.fintech.service;

import com.example.fintech.production.domain.Production;
import com.example.fintech.production.domain.ProductionCategory;
import com.example.fintech.production.repository.ProductionCategoryRepository;
import com.example.fintech.production.repository.ProductionRepository;
import com.example.fintech.production.service.ProductionService;
import com.example.fintech.production.type.InterestPaymentMethod;
import com.example.fintech.production.type.NumMonthlyPayments;
import com.example.fintech.production.type.ProductionStatus;
import com.example.fintech.production.type.ProductionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductionServiceTest {
    @Mock
    private ProductionRepository productionRepository;

    @Mock
    private ProductionCategoryRepository productionCategoryRepository;

    @InjectMocks
    private ProductionService productionService;

    @Test
    @DisplayName("계좌 상품 등록 서비스 테스트")
    void createProductionSuccess() {
        //given
        given(productionCategoryRepository.findById(anyLong()))
                .willReturn(Optional.of(ProductionCategory.builder()
                                .id(2L)
                                .productionType(ProductionType.FREE_SAVINGS)
                                .build()));

        given(productionRepository.save(any()))
                .willReturn(getProduction());

        //when
        productionService.createProduction(2L, "프리 적금",
                "매일, 매주, 매월 자유롭게 적금 운용해보세요. 매달 최대 300만원까지 저금이 가능합니다.",
                6, 2.0,
                "maturity_date", "free",
                3000000L);

        //then
        verify(productionRepository).save(any());
    }

    @Test
    @DisplayName("이미 존재하는 계좌 상품 등록 시 예외 발생 테스트")
    void createProductionWithThrowsException() {
        // given
        given(productionRepository.existsByProductionTitle(any()))
                .willReturn(true);

        // when, then
        assertThrows(RuntimeException.class, () ->
                productionService.createProduction(2L, "프리 적금",
                        "매일, 매주, 매월 자유롭게 적금 운용해보세요. 매달 최대 300만원까지 저금이 가능합니다.",
                        6, 2.0,
                        "maturity_date", "free",
                        3000000L)
        );
    }

    @Test
    @DisplayName("계좌 상품 판매 중지 서비스 테스트")
    void stopProductionSuccess() {
        //given
        Production production = getProduction();
        given(productionRepository.findById(anyLong()))
                .willReturn(Optional.of(production));

        //when
        productionService.stopProduction(1L);

        //then
        assertThat(production.getProductionStatus()).isEqualTo(ProductionStatus.SUSPENSION_OF_SALES);
    }

    private Production getProduction() {
        return Production.builder()
                .productionCategory(ProductionCategory.builder()
                        .id(2L)
                        .productionType(ProductionType.FREE_SAVINGS)
                        .build())
                .productionTitle("프리 적금")
                .productionContents("매일, 매주, 매월 자유롭게 적금 운용해보세요. 매달 최대 300만원까지 저금이 가능합니다.")
                .contractPeriod(6)
                .interestRate(2.0)
                .interestPaymentMethod(InterestPaymentMethod.MATURITY_DATE)
                .numMonthlyPayments(NumMonthlyPayments.FREE)
                .maxMonthlySavings(3000000L)
                .productionStatus(ProductionStatus.ON_SALE)
                .build();
    }

}
