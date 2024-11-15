package com.example.BE.auth.dto;

import com.example.BE.user.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class SecurityUser extends User {
    private UserEntity user;

    public SecurityUser(UserEntity user) {
        super(user.getId(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
