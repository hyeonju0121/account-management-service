package com.example.fintech.production.controller;

import com.example.fintech.production.dto.CreateProduction;
import com.example.fintech.production.dto.StopProduction;
import com.example.fintech.production.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductionController {
    private final ProductionService productionService;

    /**
     * 계좌 상품 등록 API
     */
    @PostMapping("/production")
    public CreateProduction.Response createProduction(
            @RequestBody CreateProduction.Request request) {

        var result = this.productionService.createProduction(
                        request.getProductionCategoryId(),
                        request.getProductionTitle(),
                        request.getProductionContents(),
                        request.getContractPeriod(),
                        request.getInterestRate(),
                        request.getInterestPaymentMethod(),
                        request.getNumMonthlyPayments(),
                        request.getMaxMonthlySavings());
        return CreateProduction.Response.from(result);
    }

    /**
     * 계좌 상품 판매 중지 API
     */
    @PutMapping ("/production/stop")
    public StopProduction.Response stopProduction(
            @RequestParam Long productionId){

        var result = this.productionService.stopProduction(productionId);
        return StopProduction.Response.from(result);
    }
}
