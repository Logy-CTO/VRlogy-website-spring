package com.example.demo.license;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    public Map<String, Object> getLicenseInfoByUsername(String username) {
        Map<String, Object> response = new HashMap<>();
        Optional<License> optionalLicense = licenseRepository.findByMemberId(username);

        if (optionalLicense.isPresent() && optionalLicense.get().getIsPurchased() == 1) {
            response.put("status", "Valid");
            response.put("expirationDate", optionalLicense.get().getExpirationDate());
        } else {
            response.put("status", "Invalid");
        }

        return response;
    }
}
