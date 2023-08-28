package com.example.fintech.production.service;

import com.example.fintech.production.domain.Production;
import com.example.fintech.production.domain.ProductionCategory;
import com.example.fintech.production.dto.ProductionDto;
import com.example.fintech.production.repository.ProductionCategoryRepository;
import com.example.fintech.production.repository.ProductionRepository;
import com.example.fintech.production.type.InterestPaymentMethod;
import com.example.fintech.production.type.NumMonthlyPayments;
import com.example.fintech.production.type.ProductionStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class ProductionService {

    private final ProductionCategoryRepository productionCategoryRepository;
    private final ProductionRepository productionRepository;

    /**
     * 계좌 상품 등록
     */
    @Transactional
    public ProductionDto createProduction(Long productionCategoryId,
                                          String productionTitle,
                                          String productionContents,
                                          int contractPeriod,
                                          double interestRate,
                                          String interestPaymentMethod,
                                          String numMonthlyPayments,
                                          Long maxMonthlySavings) {

        // 해당 계좌 상품 이름이 존재하는 경우 오류 처리
        if(this.productionRepository.existsByProductionTitle(productionTitle)){
            throw new RuntimeException("해당 계좌 상품이 존재합니다.");
        }

        ProductionCategory productionCategory =
                this.productionCategoryRepository.findById(productionCategoryId)
                    .orElseThrow(() -> new RuntimeException("해당 계좌 상품이 존재하지 않습니다."));

        // 자유적금(2), 정기적금(3) 에 해당하는 경우,
        // 매월 최대 저금 금액이 입력되지 않아있으면 오류 처리
        if(productionCategory.getId() > 1 && maxMonthlySavings == null){
            throw new RuntimeException("매월 최대 저금 금액이 입력되지 않았습니다.");
        }

        // String -> enum  타입 변경
        InterestPaymentMethod paymentMethod =
                InterestPaymentMethod.from(interestPaymentMethod);
        NumMonthlyPayments monthlyPayments =
                NumMonthlyPayments.from(numMonthlyPayments);

        Production production = Production.builder()
                .productionCategory(productionCategory)
                .productionTitle(productionTitle)
                .productionContents(productionContents)
                .contractPeriod(contractPeriod)
                .interestRate(interestRate)
                .interestPaymentMethod(paymentMethod)
                .numMonthlyPayments(monthlyPayments)
                .maxMonthlySavings(maxMonthlySavings)
                .productionStatus(ProductionStatus.ON_SALE)
                .build();

        this.productionRepository.save(production);

        return ProductionDto.fromEntity(production);
    }

    /**
     * 계좌 상품 판매 중지
     */
    @Transactional
    public ProductionDto stopProduction(Long productionId) {

        // 해당 계좌 상품 이름이 존재하는 경우 오류 처리
        Production production = this.productionRepository.findById(productionId)
                .orElseThrow(() -> new RuntimeException("해당 계좌 상품이 존재하지 않습니다."));

        production.setProductionStatus(ProductionStatus.SUSPENSION_OF_SALES);

        this.productionRepository.save(production);

        return ProductionDto.fromEntity(production);
    }
}
