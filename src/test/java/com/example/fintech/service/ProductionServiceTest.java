package com.example.fintech.service;

import com.example.fintech.production.domain.Production;
import com.example.fintech.production.domain.ProductionCategory;
import com.example.fintech.production.dto.ProductionDto;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void stopProduction() {
        //given
        Production production = getProduction();
        given(productionRepository.findById(anyLong()))
                .willReturn(Optional.of(production));

        ArgumentCaptor<Production> captor = ArgumentCaptor.forClass(Production.class);

        //when
        ProductionDto productionDto = productionService.stopProduction(1L);

        //then
        verify(productionRepository, times(1)).save(captor.capture());
        assertEquals(2L, productionDto.getProductionId());
        assertEquals(ProductionStatus.SUSPENSION_OF_SALES, captor.getValue().getProductionStatus());
    }

    @Test
    @DisplayName("계좌 상품 수정 서비스 테스트")
    void updateProductionSuccess() {
        //given
        Long productionId = 1L;
        String updatedTitle = "Updated Title";
        String updatedContents = "Updated Contents";

        Production production = new Production();
        production.setId(productionId);
        when(productionRepository.findById(productionId)).thenReturn(Optional.of(production));

        // When
        ProductionDto result = productionService.updateProduction(productionId, updatedTitle, updatedContents);

        // Then
        assertThat(result.getProductionTitle()).isEqualTo(updatedTitle);
        assertThat(result.getProductionContents()).isEqualTo(updatedContents);

        verify(productionRepository).findById(productionId);
        verify(productionRepository).save(production);
    }

    @Test
    @DisplayName("계좌 상품 전체 조회 서비스 테스트")
    void getAllProductionsSuccess() {
        //given
        List<Production> productions = new ArrayList<>();
        productions.add(getProduction());
        productions.add(getProduction());

        given(productionRepository.findAll()).willReturn(productions);

        //when
        List<ProductionDto> productionDtoList = productionService.getAllProduction();

        //then
        assertThat(productionDtoList.size()).isEqualTo(2);

        verify(productionRepository, times(1)).findAll(); // findAll 한 번만 호출되는 지 검증
    }

    @Test
    @DisplayName("계좌 상품 단건 조회 서비스 테스트")
    void getOneProductionSuccess() {
        //given
        Long productionId = 2L;

        Production production = getProduction();

        given(productionRepository.findById(productionId))
                .willReturn(Optional.of(production));

        //when
        ProductionDto productionDto = productionService.getOneProduction(productionId);

        //then
        assertThat(productionDto.getProductionId()).isEqualTo(production.getId());
    }


    private Production getProduction() {
        return Production.builder()
                .productionCategory(ProductionCategory.builder()
                        .id(2L)
                        .productionType(ProductionType.FREE_SAVINGS)
                        .build())
                .id(2L)
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
