package com.example.fintech.account.dto;

import com.example.fintech.production.type.ProductionType;
import lombok.*;

import java.time.LocalDateTime;

public class CreateAccount {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String memberId;
        private Long productionId; // 가입할 계좌 상품 번호
        private Long balance;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String memberId;
        private String memberName;
        private String accountNumber;
        private Long productionId;
        private ProductionType productionType;
        private LocalDateTime registeredAt;
        private LocalDateTime maturityAt;

        public static Response from(AccountDto accountDto){
            return Response.builder()
                    .memberId(accountDto.getMemberId())
                    .memberName(accountDto.getMemberName())
                    .accountNumber(accountDto.getAccountNumber())
                    .productionId(accountDto.getProduction())
                    .productionType(accountDto.getProductionType())
                    .registeredAt(accountDto.getRegisteredAt())
                    .maturityAt(accountDto.getMaturityAt())
                    .build();
        }
    }
}
