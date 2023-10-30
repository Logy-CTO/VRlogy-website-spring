package com.example.demo.service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.MemberInfo;
import com.example.demo.domain.PurchasedCart;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PurchasedCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    public void processPurchase(String memberId) {
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

            purchasedCartRepository.save(purchasedCart);
        }

        cartRepository.deleteByMemberId(memberId);
    }

}