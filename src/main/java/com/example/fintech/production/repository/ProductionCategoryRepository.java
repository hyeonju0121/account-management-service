package com.example.fintech.production.repository;

import com.example.fintech.production.domain.ProductionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionCategoryRepository extends JpaRepository<ProductionCategory, Long> {
}
