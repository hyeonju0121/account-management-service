package com.example.fintech.production.type;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 매월 납입 횟수
 */
public enum NumMonthlyPayments {
    FREE,
    ONCE_A_MONTH;

    @JsonCreator
    public static NumMonthlyPayments from(String s) {
        return NumMonthlyPayments.valueOf(s.toUpperCase());
    }
}
