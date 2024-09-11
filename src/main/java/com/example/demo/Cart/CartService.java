package com.example.demo.Cart;

import com.example.demo.Member.MemberInfo;
import com.example.demo.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PurchasedCartRepository purchasedCartRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Cart> getCartItemsByMemberId(String memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deleteCartItem(String memberId, String productName) {
        cartRepository.deleteByMemberIdAndProductName(memberId, productName);
    }

    @Transactional
    public void processPurchase(String memberId, String applyNum) {
        List<Cart> cartItems = cartRepository.findByMemberId(memberId);
        logger.info("Found {} cart items for memberId: {}", cartItems.size(), memberId);

        MemberInfo member = memberRepository.findByUsername(memberId);
        logger.info("Member info: {}", member);

        if (member == null) {
            throw new RuntimeException("The id is not inquired in the membership information. " + memberId);
        }

        for (Cart cartItem : cartItems) {
            logger.info("Processing cart item: {}", cartItem);
            PurchasedCart purchasedCart = new PurchasedCart();
            purchasedCart.setProductName(cartItem.getProductName());
            purchasedCart.setTotalPrice(cartItem.getAmount());

            Date currentDate = new Date();
            purchasedCart.setPurchaseDate(currentDate); // 현재 날짜 및 시간 설정

            String address = member.getAddress();
            purchasedCart.setShippingAddress(address);
            purchasedCart.setMemberId(memberId);
            purchasedCart.setApplyNum(applyNum);

            purchasedCartRepository.save(purchasedCart);
            logger.info("Saved purchased cart: {}", purchasedCart);
        }

        // 카트에서 해당 멤버의 상품 삭제
        cartRepository.deleteByMemberId(memberId);
        logger.info("Deleted cart items for memberId: {}", memberId);
    }
}