package com.example.demo.controller;

import com.example.demo.domain.Cart;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart")
    public String getCart() {
        return "cart";
    }

    @PostMapping("/cart")
    @ResponseBody
    public Map<String, String> addToCart(@RequestParam String memberId, @RequestParam String productName, @RequestParam BigDecimal amount) {
        Map<String, String> response = new HashMap<>();
        try {
            Cart cart = new Cart();
            cart.setMemberId(memberId);
            cart.setProductName(productName);
            cart.setAmount(amount); // 금액 설정

            cartRepository.save(cart);

            response.put("status", "success");
            response.put("message", "Product added to cart successfully!");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error while adding product to cart");
        }
        return response;
    }


    @Autowired
    private CartService cartService;

    @GetMapping("/get-cart-items")
    public ResponseEntity<List<Cart>> getCartItems(@RequestParam String memberId) {
        List<Cart> cartItems = cartService.getCartItemsByMemberId(memberId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }
}