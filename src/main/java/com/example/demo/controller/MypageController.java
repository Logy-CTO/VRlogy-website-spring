package com.example.demo.controller;

import com.example.demo.domain.MemberInfo;
import com.example.demo.service.MemberService;
import com.example.demo.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private LicenseService licenseService;

    @GetMapping
    public String mypage() {
        return "mypage";
    }

    @PostMapping("/updateName")
    @ResponseBody
    public ResponseEntity<?> updateName(@RequestParam String newName, HttpSession session) {
        String username = (String) session.getAttribute("username");



        boolean success = memberService.updateName(username, newName);
        return createResponse(success);
    }

    @PostMapping("/updateEmail")
    @ResponseBody
    public ResponseEntity<?> updateEmail(@RequestParam String newEmail, HttpSession session) {
        String username = (String) session.getAttribute("username");
        boolean emailExists = memberService.isEmailExists(newEmail);

        if (emailExists) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다");
        }
        else {
            boolean success = memberService.updateEmail(username, newEmail);
            return createResponse(success);
        }
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@RequestParam String newPassword, HttpSession session) {
        String username = (String) session.getAttribute("username");
        boolean success = memberService.updatePassword(username, newPassword);
        return createResponse(success);
    }

    @PostMapping("/updatePhoneNumber")
    @ResponseBody
    public ResponseEntity<?> updatePhoneNumber(@RequestParam String newPhoneNumber, HttpSession session) {
        String username = (String) session.getAttribute("username");
        boolean success = memberService.updatePhoneNumber(username, newPhoneNumber);
        return createResponse(success);
    }

    @PostMapping("/updateAddress")
    @ResponseBody
    public ResponseEntity<?> updateAddress(@RequestParam String newAddress, HttpSession session) {
        String username = (String) session.getAttribute("username");
        boolean success = memberService.updateAddress(username, newAddress);
        return createResponse(success);
    }

    @PostMapping("/updatePostcode")
    @ResponseBody
    public ResponseEntity<?> updatePostcode(@RequestParam String newPostcode, HttpSession session) {
        String username = (String) session.getAttribute("username");
        boolean success = memberService.updatePostcode(username, newPostcode);
        return createResponse(success);
    }

    private ResponseEntity<?> createResponse(boolean success) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getCurrentUserInfo")
    @ResponseBody
    public ResponseEntity<?> getCurrentUserInfo(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.badRequest().body("User not logged in");
        }

        MemberInfo memberInfo = memberService.getMemberInfoByUsername(username);
        if (memberInfo == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("name", memberInfo.getName());
        response.put("email", memberInfo.getUsername()); // email is stored in the 'username' field
        response.put("phoneNumber", memberInfo.getPhoneNumber());
        response.put("address", memberInfo.getAddress());
        response.put("postcode", memberInfo.getPostcode());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/licenseInfo")
    public ResponseEntity<?> getLicenseInfo(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.badRequest().body("User not logged in");
        }

        Map<String, Object> licenseInfo = licenseService.getLicenseInfoByUsername(username);
        return ResponseEntity.ok(licenseInfo);
    }

    @GetMapping("/checkRequiredInfo")
    @ResponseBody
    public ResponseEntity<?> checkRequiredInfo(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.badRequest().body("User not logged in");
        }

        MemberInfo memberInfo = memberService.getMemberInfoByUsername(username);
        if (memberInfo == null) {
            return ResponseEntity.ok(false); // 정보가 없으면 false 반환
        } else {
            boolean isMissingInfo =
                    memberInfo.getAddress() == null ||
                            memberInfo.getName() == null ||
                            memberInfo.getPostcode() == null ||
                            memberInfo.getPhoneNumber() == null;

            return ResponseEntity.ok(isMissingInfo); // 필수 정보가 누락되었으면 true 반환
        }
    }
}