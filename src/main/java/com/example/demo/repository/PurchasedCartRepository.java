package com.example.demo.repository;

import com.example.demo.domain.PurchasedCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedCartRepository extends JpaRepository<PurchasedCart, Integer> {
}