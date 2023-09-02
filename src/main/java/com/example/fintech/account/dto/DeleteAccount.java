package com.example.fintech.account.dto;

import com.example.fintech.account.type.AccountStatus;
import com.example.fintech.production.type.ProductionType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class DeleteAccount {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private String memberId;
        @NotBlank
        private String accountNumber;
        @NotBlank
        @Size(min = 6, max = 6, message = "6자리 간편비밀번호를 입력해주세요.")
        private String simplePassword;

        private String transferAccountNumber; // 적금 상품 해지시, 이체받을 계좌번호
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
        private ProductionType productionType;
        private AccountStatus accountStatus;
        private LocalDateTime unRegisteredAt;

        public static DeleteAccount.Response from(AccountDto accountDto){
            return Response.builder()
                    .memberId(accountDto.getMemberId())
                    .memberName(accountDto.getMemberName())
                    .accountNumber(accountDto.getAccountNumber())
                    .productionType(accountDto.getProductionType())
                    .accountStatus(accountDto.getAccountStatus())
                    .unRegisteredAt(accountDto.getUnregisteredAt())
                    .build();
        }
    }

}
