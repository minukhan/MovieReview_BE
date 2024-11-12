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

import java.util.Optional;

@Service
public class SecurityUserDetailService implements UserDetailsService {
    // 사용자의 인증 정보를 데이터베이스에서 조회

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
//        Optional<UserEntity> optionalUser = userRepository.findByEmail(id);
//        if(!optionalUser.isPresent()) {
//            // 존재하지 않을 경우
//            throw new UsernameNotFoundException(id + " 사용자 없음");
//        } else{
//            UserEntity user = optionalUser.get();
//            return new SecurityUser(user);
//        }
        UserEntity user = userRepository.findById(id);
        if(user == null) {
            return null;
        } else{
            return new SecurityUser(user);
        }
    }
}
