package com.example.fintech.production.controller;

import com.example.fintech.production.dto.CreateProduction;
import com.example.fintech.production.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        return null;
    }
}
