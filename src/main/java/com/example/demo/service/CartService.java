package com.example.demo.service;

import com.example.demo.domain.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCartItemsByMemberId(String memberId) {
        return cartRepository.findByMemberId(memberId);
    }
}