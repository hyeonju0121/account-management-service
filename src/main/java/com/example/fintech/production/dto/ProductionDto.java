package com.example.fintech.production.dto;

import com.example.fintech.production.domain.Production;
import com.example.fintech.production.type.InterestPaymentMethod;
import com.example.fintech.production.type.NumMonthlyPayments;
import com.example.fintech.production.type.ProductionStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionDto {
    private Long productionId;
    private String productionTitle;
    private String productionContents;
    private int contractPeriod;
    private double interestRate;
    private InterestPaymentMethod interestPaymentMethod;
    private NumMonthlyPayments numMonthlyPayments;
    private Long maxMonthlySavings;
    private ProductionStatus productionStatus;
    private Long totalAccountsNums;

    // Entity -> Dto 변환
    public static ProductionDto fromEntity(Production production) {
        return ProductionDto.builder()
                .productionId(production.getId())
                .productionTitle(production.getProductionTitle())
                .productionContents(production.getProductionContents())
                .contractPeriod(production.getContractPeriod())
                .interestRate(production.getInterestRate())
                .interestPaymentMethod(production.getInterestPaymentMethod())
                .numMonthlyPayments(production.getNumMonthlyPayments())
                .maxMonthlySavings(production.getMaxMonthlySavings())
                .productionStatus(production.getProductionStatus())
                .totalAccountsNums(production.getTotalAccountsNum())
                .build();
    }
}
