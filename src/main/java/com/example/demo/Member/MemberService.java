package com.example.demo.Member;

import com.example.demo.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean updateName(String username, String newName) {
        MemberInfo member = memberRepository.findByUsername(username);
        if (member != null) {
            member.setName(newName);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean isEmailExists(String newEmail) {
        return memberRepository.existsByUsername(newEmail);
    }

    @Transactional
    public boolean updateEmail(String currentUsername, String newEmail) {
        MemberInfo member = memberRepository.findByUsername(currentUsername);
        if (member != null) {
            member.setUsername(newEmail);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updatePassword(String username, String newPassword) {
        MemberInfo member = memberRepository.findByUsername(username);
        if (member != null) {
            member.setPassword(passwordEncoder.encode(newPassword)); // 비밀번호 암호화
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updatePhoneNumber(String username, String newPhoneNumber) {
        MemberInfo member = memberRepository.findByUsername(username);
        if (member != null) {
            member.setPhoneNumber(newPhoneNumber);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateAddress(String username, String newAddress) {
        MemberInfo member = memberRepository.findByUsername(username);
        if (member != null) {
            member.setAddress(newAddress);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updatePostcode(String username, String newPostcode) {
        MemberInfo member = memberRepository.findByUsername(username);
        if (member != null) {
            member.setPostcode(newPostcode);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    public boolean isRequiredInfoMissing(String username) {
        MemberInfo member = memberRepository.findByUsername(username);
        return member == null || member.getName() == null || member.getAddress() == null || member.getPhoneNumber() == null || member.getPostcode() == null;
    }

    public MemberInfo getMemberInfoByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
