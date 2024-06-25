package com.example.demo.license;

import com.example.demo.Member.MemberInfo;
import com.example.demo.Member.MemberRepository;
import com.example.demo.license.License;
import com.example.demo.license.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private MemberRepository memberInfoRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, String> authenticate(String username, String password) {
        Map<String, String> response = new HashMap<>();
        MemberInfo member = memberInfoRepository.findByUsername(username);

        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            response.put("status", "error");
            response.put("message", "Invalid username or password.");
            return response;
        }

        Optional<License> optionalLicense = licenseRepository.findByMemberId(username);

        if (!optionalLicense.isPresent()) {
            response.put("status", "nolicense");
            response.put("message", "License not found.");
        } else {
            License license = optionalLicense.get();
            if (license.getIsPurchased() != 1) {
                response.put("status", "nolicense");
                response.put("message", "License not purchased.");
            } else {
                response.put("status", "success");
                response.put("message", "Login successful.");
            }
        }

        return response;
    }
}
