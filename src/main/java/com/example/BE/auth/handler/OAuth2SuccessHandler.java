package com.example.BE.auth.handler;


import com.example.BE.auth.entity.CustomOAuth2UserEntity;
import com.example.BE.auth.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2UserEntity oAuth2User = (CustomOAuth2UserEntity) authentication.getPrincipal();

        String id = oAuth2User.getName();
        String token = jwtProvider.create(id);

        // 발급된 토큰을 쿠키에 저장
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 환경에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일간 유효


        response.addCookie(cookie);
        response.sendRedirect("https://localhost:3000/");
    }
}