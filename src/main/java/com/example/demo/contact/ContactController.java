package com.example.demo.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private ContactFormRepository contactFormRepository;

    @PostMapping("/contact")
    public String submitContactForm(ContactForm contactForm, Model model) {
        try {
            contactFormRepository.save(contactForm);
            model.addAttribute("message", "Your message has been sent successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while sending your message.");
            e.printStackTrace();
        }
        return "redirect:/"; // 메시지 전송 후 리디렉션할 페이지 이름
    }
}
