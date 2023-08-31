package com.example.fintech.account.domain;

import com.example.fintech.account.type.AccountStatus;
import com.example.fintech.member.domain.Member;
import com.example.fintech.production.domain.Production;
import com.example.fintech.production.type.ProductionType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GenericGenerator(name = "USER_GENERATOR", strategy = "uuid")
    private String accountNumber;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Production production;

    @Enumerated(EnumType.STRING)
    private ProductionType productionType;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime maturityAt;
    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
