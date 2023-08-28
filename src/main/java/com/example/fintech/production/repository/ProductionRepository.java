package com.example.fintech.production.repository;

import com.example.fintech.production.domain.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
    boolean existsByProductionTitle(String productionTitle);
}
