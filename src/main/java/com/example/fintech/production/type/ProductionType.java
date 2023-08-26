package com.example.fintech.production.type;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 계좌 상품 종류
 */
public enum ProductionType {
    SAVINGS_ACCOUNT,        // 입출금
    INSTALMENT_SAVINGS,     // 정기적금
    FREE_SAVINGS;           // 자유적금

    @JsonCreator
    public static ProductionType from(String s) {
        return ProductionType.valueOf(s.toUpperCase());
    }
}
