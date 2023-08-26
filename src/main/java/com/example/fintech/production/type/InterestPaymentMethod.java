package com.example.fintech.production.type;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 이자 지급 방식
 */
public enum InterestPaymentMethod {
    THE_FIRST_DAY_OF_EVERY_MONTH,
    MATURITY_DATE;

    @JsonCreator
    public static InterestPaymentMethod from(String s) {
        return InterestPaymentMethod.valueOf(s.toUpperCase());
    }
}
