package com.example.demo.license;

import com.example.demo.license.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        Map<String, String> response = authService.authenticate(username, password);

        if ("success".equals(response.get("status"))) {
            session.setAttribute("username", username);
        }

        return response;
    }

    @GetMapping("/csrf-token")
    public Map<String, String> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        Map<String, String> response = new HashMap<>();
        response.put("csrfToken", csrfToken.getToken());
        return response;
    }
}
