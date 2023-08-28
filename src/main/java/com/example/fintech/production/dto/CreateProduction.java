package com.example.fintech.production.dto;

import com.example.fintech.production.type.InterestPaymentMethod;
import com.example.fintech.production.type.NumMonthlyPayments;
import lombok.*;

public class CreateProduction {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private Long productionCategoryId;
        private String productionTitle;
        private String productionContents;
        private int contractPeriod;
        private double interestRate;
        private String interestPaymentMethod;
        private String numMonthlyPayments;
        private Long maxMonthlySavings;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long productionId;
        private String productionTitle;
        private String productionContents;
        private int contractPeriod;
        private double interestRate;
        private InterestPaymentMethod interestPaymentMethod;
        private NumMonthlyPayments numMonthlyPayments;
        private Long maxMonthlySavings;

        public static Response from(ProductionDto productionDto){
            return Response.builder()
                    .productionId(productionDto.getProductionId())
                    .productionTitle(productionDto.getProductionTitle())
                    .productionContents(productionDto.getProductionContents())
                    .contractPeriod(productionDto.getContractPeriod())
                    .interestRate(productionDto.getInterestRate())
                    .interestPaymentMethod(productionDto.getInterestPaymentMethod())
                    .numMonthlyPayments(productionDto.getNumMonthlyPayments())
                    .maxMonthlySavings(productionDto.getMaxMonthlySavings())
                    .build();
        }
    }
}
