package com.example.BE.auth.service;

import com.example.BE.auth.dto.SecurityUser;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        UserEntity user = userRepository.findById(id);
        if(user == null) {
            return null;
        } else{
            return new SecurityUser(user);
        }
    }
}
