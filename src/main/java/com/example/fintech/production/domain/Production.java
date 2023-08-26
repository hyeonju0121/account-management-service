package com.example.fintech.production.domain;

import com.example.fintech.production.type.InterestPaymentMethod;
import com.example.fintech.production.type.NumMonthlyPayments;
import com.example.fintech.production.type.ProductionStatus;
import lombok.*;
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
public class Production {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ProductionCategory productionCategory;

    private String productionTitle;
    private String productionContents;

    private int contractPeriod;
    private double interestRate;

    @Enumerated(EnumType.STRING)
    private InterestPaymentMethod interestPaymentMethod;
    @Enumerated(EnumType.STRING)
    private NumMonthlyPayments numMonthlyPayments;

    private Long maxMonthlySavings;

    @Enumerated(EnumType.STRING)
    private ProductionStatus productionStatus;

    private Long totalAccountsNum;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
