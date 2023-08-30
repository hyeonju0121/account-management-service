package com.example.fintech.account.dto;

import com.example.fintech.account.domain.Account;
import com.example.fintech.production.type.ProductionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private String accountNumber;
    private Long production;
    private ProductionType productionType;
    private Long userId; // 회원 식별 번호
    private String memberId; // 회원 아이디
    private String memberName;

    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime maturityAt;
    private LocalDateTime unregisteredAt;

    // Entity -> Dto 변환
    public static AccountDto fromEntity(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .production(account.getProduction().getId())
                .productionType(account.getProductionType())
                .userId(account.getMember().getId())
                .memberId(account.getMember().getMemberId())
                .memberName(account.getMember().getName())
                .balance(account.getBalance())
                .registeredAt(account.getRegisteredAt())
                .maturityAt(account.getMaturityAt())
                .unregisteredAt(account.getUnregisteredAt())
                .build();
    }
}
