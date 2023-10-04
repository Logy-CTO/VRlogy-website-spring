package com.example.demo.controller;

import com.example.demo.domain.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
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
    public Map<String, String> addToCart(@RequestParam Integer memberId, @RequestParam String productName) {
        Map<String, String> response = new HashMap<>();
        try {
            Cart cart = new Cart();
            cart.setMemberId(memberId);
            cart.setProductName(productName);
            cartRepository.save(cart);
            response.put("status", "success");
            response.put("message", "Product added to cart successfully!");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error while adding product to cart");
        }
        return response;
    }
}