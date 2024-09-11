package com.example.demo.Cart;

import com.example.demo.license.License;
import com.example.demo.license.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private LicenseRepository licenseRepository;

    @GetMapping("/cart")
    public String getCart() {
        return "cart";
    }

    @PostMapping("/cart")
    @ResponseBody
    public Map<String, String> addToCart(@RequestBody Map<String, Object> requestBody) {
        Map<String, String> response = new HashMap<>();
        try {
            String memberId = (String) requestBody.get("memberId");
            String productName = (String) requestBody.get("productName");
            BigDecimal amount = new BigDecimal(requestBody.get("amount").toString());

            Cart cart = new Cart();
            cart.setMemberId(memberId);
            cart.setProductName(productName);
            cart.setAmount(amount);

            cartRepository.save(cart);

            response.put("status", "success");
            response.put("message", "added to shopping cart.");
        } catch (Exception e) {
            logger.error("Error while adding to cart", e);
            response.put("status", "error");
            response.put("message", "Error while adding to your cart.");
        }
        return response;
    }

    @GetMapping("/get-cart-items")
    public ResponseEntity<List<Cart>> getCartItems(@RequestParam String memberId) {
        List<Cart> cartItems = cartService.getCartItemsByMemberId(memberId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/delete-cart-item")
    @ResponseBody
    public Map<String, String> deleteCartItem(@RequestBody Map<String, String> requestBody) {
        Map<String, String> response = new HashMap<>();
        String memberId = requestBody.get("memberId");
        String productName = requestBody.get("productName");
        try {
            cartService.deleteCartItem(memberId, productName);
            response.put("status", "success");
            response.put("message", "deleted the item from my shopping cart");
        } catch (Exception e) {
            logger.error("Error while deleting cart item", e);
            response.put("status", "error");
            response.put("message", "Error while deleting cart item.");
        }
        return response;
    }

    @PostMapping("/apply-license")
    @ResponseBody
    public Map<String, String> applyLicense(@RequestBody Map<String, String> requestBody) {
        Map<String, String> response = new HashMap<>();
        try {
            String memberId = requestBody.get("memberId");
            List<Cart> cartItems = cartService.getCartItemsByMemberId(memberId);
            boolean hasVRlogyProgram = cartItems.stream().anyMatch(item -> "VRlogy Program".equals(item.getProductName()));

            if (hasVRlogyProgram) {
                License license = licenseRepository.findByMemberId(memberId).orElse(new License());
                license.setMemberId(memberId);
                license.setIsPurchased(1);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, 1);
                license.setExpirationDate(calendar.getTime());

                licenseRepository.save(license);

                response.put("status", "success");
                response.put("message", "license registered successfully.");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error occurred in the license registration.");
        }
        return response;
    }

    @PostMapping("/process-purchase")
    @ResponseBody
    public Map<String, String> processPurchase(@RequestBody Map<String, String> requestBody) {
        Map<String, String> response = new HashMap<>();
        try {
            String memberId = requestBody.get("memberId");
            String applyNum = requestBody.get("applyNum");
            logger.info("Processing purchase for memberId: {}, applyNum: {}", memberId, applyNum);
            cartService.processPurchase(memberId, applyNum);
            response.put("status", "success");
            response.put("message", "The purchase was successful.");
        } catch (Exception e) {
            logger.error("Error in your purchase", e);
            response.put("status", "error");
            response.put("message", "Error in your purchase.");
        }
        return response;
    }
}