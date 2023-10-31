package com.example.demo.service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.MemberInfo;
import com.example.demo.domain.PurchasedCart;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PurchasedCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PurchasedCartRepository purchasedCartRepository;

    public List<Cart> getCartItemsByMemberId(String memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deleteCartItem(String memberId, String productName) {
        cartRepository.deleteByMemberIdAndProductName(memberId, productName);
    }

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void processPurchase(String memberId, String applyNum) {
        List<Cart> cartItems = cartRepository.findByMemberId(memberId);

        MemberInfo member = memberRepository.findByUsername(memberId);

        if (member == null) {
            throw new RuntimeException("MemberInfo not found for memberId: " + memberId);
        }

        for (Cart cartItem : cartItems) {
            PurchasedCart purchasedCart = new PurchasedCart();
            purchasedCart.setProductName(cartItem.getProductName());
            purchasedCart.setAmount(cartItem.getAmount());
            purchasedCart.setMemberId(cartItem.getMemberId());

            // Set other properties from MemberInfo
            purchasedCart.setName(member.getName());
            purchasedCart.setAddress(member.getAddress());
            purchasedCart.setPostcode(member.getPostcode());
            purchasedCart.setPhoneNumber(member.getPhoneNumber());

            purchasedCart.setApplyNum(applyNum); // applyNum 설정

            purchasedCartRepository.save(purchasedCart);
        }

        cartRepository.deleteByMemberId(memberId);
    }

}