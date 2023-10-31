package com.example.demo.service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.MemberInfo;
import com.example.demo.domain.PurchasedCart;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PurchasedCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
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
            throw new RuntimeException("회원정보에 id가 조회되지 않습니다. " + memberId);
        }

        for (Cart cartItem : cartItems) {
            PurchasedCart purchasedCart = new PurchasedCart();
            purchasedCart.setProductName(cartItem.getProductName());
            purchasedCart.setTotalPrice(cartItem.getAmount());

            Date currentDate = new Date();
            purchasedCart.setPurchaseDate(currentDate); // 현재 날짜 및 시간 설정

            MemberInfo memberInfo = memberRepository.findByUsername(memberId);
            String address = memberInfo.getAddress();
            purchasedCart.setShippingAddress(address);
            purchasedCart.setMemberId(memberId);
            purchasedCart.setApplyNum(applyNum);

            purchasedCartRepository.save(purchasedCart);
        }

        // 카트에서 해당 멤버의 상품 삭제
        cartRepository.deleteByMemberId(memberId);
    }

}