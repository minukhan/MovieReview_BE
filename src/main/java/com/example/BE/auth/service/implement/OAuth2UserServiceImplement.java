package com.example.BE.auth.service.implement;

import com.example.BE.auth.entity.CustomOAuth2UserEntity;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        UserEntity userEntity = null;
        String userId = null;
        String email = null;

        if(oauthClientName.equals("kakao")){
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            email = (String) ((Map)oAuth2User.getAttributes().get("kakao_account")).get("email");
            userEntity = userRepository.findByUserId("kakao" + userId);
            if(userEntity == null){
                userEntity = new UserEntity(userId, email, "kakao");
                userRepository.save(userEntity);
            }

        }
        return new CustomOAuth2UserEntity(userId);
    }

}