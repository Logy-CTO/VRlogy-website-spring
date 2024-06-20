package com.example.demo.Member;

import com.example.demo.Member.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberInfo, Integer> {

    MemberInfo findByUsername(String username);
    boolean existsByUsername(String username);
    int countByUsername(String username);

}