package com.example.demo.Member;

import com.example.demo.license.LicenseService;
import com.example.demo.Calorie.CalorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private CalorieService calorieService;
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
    public ResponseEntity<?> updateName(@RequestBody UpdateRequest request, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authenticated.");
            }
            boolean success = memberService.updateName(username, request.getValue());
            if (success) {
                return ResponseEntity.ok("Updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating name.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/updateEmail")
    @ResponseBody
    public ResponseEntity<?> updateEmail(@RequestBody UpdateRequest request, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authenticated.");
            }
            boolean emailExists = memberService.isEmailExists(request.getValue());

            if (emailExists) {
                return ResponseEntity.badRequest().body("Email already exists.");
            } else {
                boolean success = memberService.updateEmail(username, request.getValue());
                if (success) {
                    return ResponseEntity.ok("Updated successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating email.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // 콘솔에 에러 로그를 출력합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/updateSessionEmail")
    @ResponseBody
    public ResponseEntity<?> updateSessionEmail(@RequestBody UpdateRequest request, HttpSession session) {
        try {
            session.setAttribute("username", request.getValue());
            return ResponseEntity.ok("Session updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@RequestBody UpdateRequest request, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authenticated.");
            }
            boolean success = memberService.updatePassword(username, request.getValue());
            if (success) {
                return ResponseEntity.ok("Updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating password.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/updatePhoneNumber")
    @ResponseBody
    public ResponseEntity<?> updatePhoneNumber(@RequestBody UpdateRequest request, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authenticated.");
            }
            boolean success = memberService.updatePhoneNumber(username, request.getValue());
            if (success) {
                return ResponseEntity.ok("Updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating phone number.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/updateAddress")
    @ResponseBody
    public ResponseEntity<?> updateAddress(@RequestBody UpdateRequest request, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authenticated.");
            }
            boolean success = memberService.updateAddress(username, request.getValue());
            if (success) {
                return ResponseEntity.ok("Updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating address.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/updatePostcode")
    @ResponseBody
    public ResponseEntity<?> updatePostcode(@RequestBody UpdateRequest request, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authenticated.");
            }
            boolean success = memberService.updatePostcode(username, request.getValue());
            if (success) {
                return ResponseEntity.ok("Updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating postcode.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @GetMapping("/licenseInfo")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getLicenseInfo(HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Map<String, Object> licenseInfo = licenseService.getLicenseInfoByUsername(username);
            return ResponseEntity.ok(licenseInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/checkRequiredInfo")
    @ResponseBody
    public ResponseEntity<Boolean> checkRequiredInfo(HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }
            boolean infoMissing = memberService.isRequiredInfoMissing(username);
            return ResponseEntity.ok(infoMissing);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping("/getCurrentUserInfo")
    @ResponseBody
    public ResponseEntity<MemberInfo> getCurrentUserInfo(HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            MemberInfo member = memberService.getMemberInfoByUsername(username);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public static class UpdateRequest {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
