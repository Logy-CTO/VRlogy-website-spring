package com.example.demo.Calorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalorieRepository extends JpaRepository<Calorie, Integer> {
    List<Calorie> findByMemberId(String memberId);
}