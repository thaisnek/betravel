package com.example.travelweb.repository;

import com.example.travelweb.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByCode(String code);
}