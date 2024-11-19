package com.autoever.cinewall.auth.config;

import java.io.IOException;

import com.autoever.cinewall.auth.filter.JwtAuthenticationFilter;
import com.autoever.cinewall.auth.handler.OAuth2SuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Slf4j
@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DefaultOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/cinewall/auth/id-check", "/cinewall/auth/email-certification", "/cinewall/auth/check-certification", "/cinewall/auth/sign-up", "/cinewall/auth/sign-in",
                                "/cinewall/auth/oauth2/kakao", "/cinewall/movie/trailer", "/cinewall/movie/latest", "/cinewall/movie/popular", "/cinewall/movie/recommend", "/cinewall/movie/review",
                                "/cinewall/movie/powerReview", "/cinewall/movie/search", "/cinewall/review/*/reviews/latest", "/cinewall/review/*/reviews/favorite", "/cinewall/movie/*/average-rating",
                                "/cinewall/movie/*/rating-distribution", "/cinewall/movieFavorite/*/isFavorite", "/cinewall/movie/*/similar-movies", "/cinewall/review/user-graph/*", "/cinewall/review/user-list/*",
                                "/cinewall/review/detail/*", "/cinewall/review/poster-list/*", "/cinewall/actor/*/recommend", "/cinewall/crew/*/recommend", "/bot/*/chat", "/cinewall/user/*/mypage","/cinewall/movieFavorite/count", "cinewall/movie/get-movie", "cinewall/genre/get-genre").permitAll() // 인증 없이 접근 가능
                        .requestMatchers("/cinewall/auth/logout", "/cinewall/follow/**", "/cinewall/movie/favorite", "/cinewall/movie/userbase", "/cinewall/movie/*/reviews", "/cinewall/movie/genre", "/cinewall/recommend/hashtag",
                                "/cinewall/reviews/*/like", "/cinewall/movie/*/reviews", "/cinewall/movie/reviews/**", "/cinewall/movieFavorite/*/favorite", "/cinewall/user/edit", "/cinewall/review/edit/*",
                                "/cinewall/review/delete/*", "/cinewall/survey/recommend", "/cinewall/survey/submit", "/cinewall/user/info", "/subscribe").hasRole("USER")

                        .requestMatchers("/cinewall/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/cinewall/auth/oauth2"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 특정 도메인에서만 접근을 허용
        corsConfiguration.addAllowedOriginPattern("http://localhost:3000");
        corsConfiguration.addAllowedOriginPattern("https://cinewalll.netlify.app");
        corsConfiguration.addAllowedOriginPattern("http://3.38.104.1:8080");
        corsConfiguration.addAllowedOriginPattern("https://api.cinewall.shop");
        corsConfiguration.addAllowedOriginPattern("https://cinewall.shop");

        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("Set-Cookie");
        corsConfiguration.setAllowCredentials(true);
        // 노출할 헤더 추가 (Authorization 헤더 노출)
        corsConfiguration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission. \"}");
    }

}
