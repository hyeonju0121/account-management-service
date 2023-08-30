package com.example.fintech.account.repository;

import com.example.fintech.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByProduction_id(Long productionId);

    List<Account> findByMember_id(Long memberId);
}
