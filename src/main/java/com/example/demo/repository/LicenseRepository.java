package com.example.demo.repository;

import com.example.demo.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseRepository extends JpaRepository<License, Integer> {
    Optional<License> findByMemberId(String memberId);
}