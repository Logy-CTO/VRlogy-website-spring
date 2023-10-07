package com.example.demo.service;

import com.example.demo.domain.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Transactional
    public void deleteCartItem(String memberId, String productName) {
        cartRepository.deleteByMemberIdAndProductName(memberId, productName);
    }
    public List<Cart> getCartItemsByMemberId(String memberId) {
        return cartRepository.findByMemberId(memberId);
    }
}