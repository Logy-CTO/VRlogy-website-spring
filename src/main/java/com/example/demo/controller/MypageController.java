package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @GetMapping
    public String mypage() {
        // 마이페이지 화면을 보여주는 로직 작성
        return "mypage"; // mypage.html (또는 다른 템플릿 파일) 반환
    }
}