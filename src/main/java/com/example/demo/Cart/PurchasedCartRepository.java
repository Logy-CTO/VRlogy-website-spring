package com.example.demo.Cart;

import com.example.demo.Cart.PurchasedCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedCartRepository extends JpaRepository<PurchasedCart, Integer> {
}