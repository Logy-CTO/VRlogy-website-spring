package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String memberId;
    private int isPurchased;
    private Date expirationDate;

    // 기본 생성자
    public License() {
    }

    // 모든 필드를 인자로 받는 생성자
    public License(String memberId, int isPurchased, Date expirationDate) {
        this.memberId = memberId;
        this.isPurchased = isPurchased;
        this.expirationDate = expirationDate;
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

    public int getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(int isPurchased) {
        this.isPurchased = isPurchased;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}