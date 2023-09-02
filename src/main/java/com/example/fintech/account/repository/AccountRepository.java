package com.example.fintech.account.repository;

import com.example.fintech.account.domain.Account;
import com.example.fintech.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByAccountNumber(String accountNumber);

    Integer countByMember(Member member);

}
