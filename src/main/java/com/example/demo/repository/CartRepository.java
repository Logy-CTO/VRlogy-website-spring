package com.example.demo.repository;

import com.example.demo.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMemberId(String memberId);
    void deleteByMemberId(String memberId);
    void deleteByMemberIdAndProductName(String memberId, String productName);
}