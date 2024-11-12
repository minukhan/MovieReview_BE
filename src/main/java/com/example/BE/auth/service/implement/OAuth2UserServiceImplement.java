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
        String id = null;
        String email = null;
        String profile_url = null;
        String nickname = null;

        if(oauthClientName.equals("kakao")){
            id = "kakao_" + oAuth2User.getAttributes().get("id");
            email = (String) ((Map)oAuth2User.getAttributes().get("kakao_account")).get("email");
            profile_url = (String)((Map)((Map)oAuth2User.getAttributes().get("kakao_account")).get("profile")).get("profile_image_url");
            nickname = (String)((Map)((Map)oAuth2User.getAttributes().get("kakao_account")).get("profile")).get("nickname");
            userEntity = userRepository.findById("kakao" + id);
            if(userEntity == null){
                userEntity = new UserEntity(id, nickname, email, "kakao", profile_url);
                userRepository.save(userEntity);
            }

        }
        return new CustomOAuth2UserEntity(id);
    }

}