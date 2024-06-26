package com.example.demo.Calorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalorieService {

    private final CalorieRepository calorieRepository;

    @Autowired
    public CalorieService(CalorieRepository calorieRepository) {
        this.calorieRepository = calorieRepository;
    }

    public List<Calorie> getCaloriesByMemberId(String memberId) {
        return calorieRepository.findByMemberId(memberId);
    }
}