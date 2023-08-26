package com.example.fintech.production.dto;

import lombok.Getter;
import lombok.Setter;

public class CreateProduction {

    @Getter
    @Setter
    public static class Request(Long productionCategoryId,
                                String title, String contents, int contractPeriod,
                                double interestRate, ) {

        //계좌 상품 식별 번호, 상품 이름, 상품 설명, 계약 기간,
        // 금리, 이자 지급 방식, 매월 납입 횟수, 매일 최대 저금 금액

    }
}
