package com.example.fintech.production.controller;

import com.example.fintech.production.dto.CreateProduction;
import com.example.fintech.production.dto.StopProduction;
import com.example.fintech.production.dto.UpdateProduction;
import com.example.fintech.production.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PutMapping ("/production/stop/{productionId}")
    public StopProduction.Response stopProduction(
            @PathVariable Long productionId){

        var result = this.productionService.stopProduction(productionId);
        return StopProduction.Response.from(result);
    }

    /**
     * 계좌 상품 수정 API
     */
    @PutMapping("/production/update/{productionId}")
    public UpdateProduction.Response updateProduction(
            @PathVariable Long productionId,
            @RequestBody UpdateProduction.Request request) {

        var result = this.productionService.updateProduction(productionId,
                request.getProductionTitle(), request.getProductionContents());

        return UpdateProduction.Response.from(result);
    }

    /**
     * 계좌 상품 전체 조회 API
     */
    @GetMapping("/production")
    public ResponseEntity<?> searchProduction() {
        var result = this.productionService.getAllProduction();
        return ResponseEntity.ok(result);
    }

    /**
     * 계좌 상품 단건 조회 API
     */
    @GetMapping("/production/{productionId}")
    public ResponseEntity<?> searchOneProduction(@PathVariable Long productionId) {
        var result = this.productionService.getOneProduction(productionId);
        return ResponseEntity.ok(result);
    }

}
