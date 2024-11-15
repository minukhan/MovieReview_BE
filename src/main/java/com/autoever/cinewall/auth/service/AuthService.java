package com.autoever.cinewall.auth.service;

import com.autoever.cinewall.auth.dto.request.*;
import com.autoever.cinewall.auth.dto.response.*;
import com.example.BE.auth.dto.request.*;
import com.example.BE.auth.dto.response.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto, String profile_url);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto, HttpServletResponse response);
    ResponseEntity<? super ResponseDto> logout(HttpServletResponse response);
}