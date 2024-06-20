package com.example.demo.Loginout;

import com.example.demo.Member.MemberInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup"; // signup과 매칭될 것입니다.
    }

    @PostMapping("/signup")
    @ResponseBody
    public Map<String, String> handleSignUp(@RequestBody Map<String, String> payload) {
        Map<String, String> response = new HashMap<>();
        String username = payload.get("username");
        String password = payload.get("password");
        String confirmPassword = payload.get("confirmPassword");

        // 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (!password.equals(confirmPassword)) {
            response.put("result", "passwordMismatch");
            return response;
        }

        // 비밀번호 유효성 검사
        if (!isPasswordValid(password)) {
            response.put("result", "invalidPassword");
            return response;
        }

        if (signUpService.isUsernameAlreadyTaken(username)) {
            response.put("result", "duplicate");
            return response;
        }

        MemberInfo member = new MemberInfo();
        member.setUsername(username);
        member.setPassword(password);

        signUpService.signUp(member);

        response.put("result", "success");

        return response; // 회원가입 성공 시 success 메시지 반환
    }

    // 비밀번호 유효성 검사 메서드 (예시로 최소 8자 이상, 숫자와 특수문자 포함)
    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }
        return true;
    }
}
