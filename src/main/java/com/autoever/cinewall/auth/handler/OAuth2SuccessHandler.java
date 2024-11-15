package com.autoever.cinewall.auth.handler;

import com.autoever.cinewall.auth.entity.CustomOAuth2UserEntity;
import com.autoever.cinewall.auth.provider.JwtProvider;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.service.UserService;
import jakarta.servlet.ServletException;
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
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2UserEntity oAuth2User = (CustomOAuth2UserEntity) authentication.getPrincipal();

        String id = oAuth2User.getName();
        String token = jwtProvider.create(id);

        UserEntity user = userService.findById(id);
        String nickname = user.getNickname();
        String profile_url = user.getProfile_url();

        response.sendRedirect("http://localhost:3000?id=" + id + "&AccessToken=" + token + "&Nickname=" + nickname + "&ProfileUrl=" + profile_url);
    }
}