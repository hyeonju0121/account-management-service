package com.example.fintech.production.type;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 계좌 상품 상태
 */
public enum ProductionStatus {
    ON_SALE,
    SUSPENSION_OF_SALES;

    @JsonCreator
    public static ProductionStatus from(String s) {
        return ProductionStatus.valueOf(s.toUpperCase());
    }
}
