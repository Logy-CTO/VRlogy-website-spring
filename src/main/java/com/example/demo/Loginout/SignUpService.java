package com.example.demo.Loginout;

import com.example.demo.Member.MemberInfo;
import com.example.demo.Member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isUsernameAlreadyTaken(String username) {
        return memberRepository.countByUsername(username) > 0;
    }

    public void signUp(MemberInfo member) {
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }
}
