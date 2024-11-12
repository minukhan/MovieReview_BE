package com.example.BE.auth.dto;

import com.example.BE.user.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.stream.Collectors;

public class SecurityUser extends User {
    // Security 유저 정보 객체 생성 클래스
    // 인증된 사용자의 정보를 나타내는데 사용

    private UserEntity user;

    public SecurityUser(UserEntity user) {
        // memberId, password, role 전달
        super(user.getId(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
