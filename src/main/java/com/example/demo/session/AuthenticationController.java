package com.example.demo.session;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    @GetMapping("/check-authentication")
    public ResponseEntity<?> checkAuthentication(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        String username = (String) session.getAttribute("username");

        if (username != null) {
            response.put("authenticated", true);
            response.put("username", username); // username을 응답에 포함
        } else {
            response.put("authenticated", false);
        }

        return ResponseEntity.ok().body(response);
    }
}