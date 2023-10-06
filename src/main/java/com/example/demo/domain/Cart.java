package com.example.demo.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 카트 아이템의 고유 ID

    private String memberId; // 사용자의 username 또는 memberId
    private String productName; // 제품 이름
    private BigDecimal amount; // 금액

    // 기본 생성자
    public Cart() {
    }

    // getter와 setter 메서드

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}