package com.example.demo.domain;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Calorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double calorie;
    private Date date;
    private String memberId;

    // Getters
    public int getId() {
        return id;
    }

    public double getCalorie() {
        return calorie;
    }

    public Date getDate() {
        return date;
    }

    public String getMemberId() {
        return memberId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMember_id(String member_id) {
        this.memberId = memberId;
    }
}