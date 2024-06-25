package com.example.demo.license;

import javax.persistence.*;
import java.util.Date;

@Entity
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String memberId;
    private int isPurchased;
    private Date expirationDate;

    public License() {
    }

    public License(String memberId, int isPurchased, Date expirationDate) {
        this.memberId = memberId;
        this.isPurchased = isPurchased;
        this.expirationDate = expirationDate;
    }

    // getters and setters
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
