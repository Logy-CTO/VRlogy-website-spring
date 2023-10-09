package com.example.demo.controller;

import com.example.demo.domain.MemberInfo;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final LoginService loginService;
    @Autowired
    private MemberRepository memberRepository;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String Home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        boolean loggedIn = loginService.login(username, password);

        if (loggedIn) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("username", username);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            // 로그인 실패 시 다른 페이지로 리다이렉션하거나, 아래와 같이 동일한 로그인 페이지를 반환하지 않도록 변경
            return "/login"; // 예: 에러 페이지로 이동
        }
    }
    }

