package com.example.demo.Loginout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Ensure this matches the name of your login template
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> processLogin(@RequestBody Map<String, String> payload, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        String username = payload.get("username");
        String password = payload.get("password");

        boolean loggedIn = loginService.login(username, password);

            if (loggedIn) {
                session.setAttribute("username", username);
                response.put("status", "success");
                response.put("message", "Login successful.");
            } else {
                response.put("status", "error");
                response.put("message", "Invalid username or password.");
            }

        return response;
    }
}
