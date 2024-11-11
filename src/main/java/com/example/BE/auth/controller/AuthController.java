package com.example.BE.auth.controller;

import com.example.BE.auth.dto.request.*;
import com.example.BE.auth.dto.response.*;
import com.example.BE.auth.service.AuthService;
import com.example.BE.s3.service.ImageUploadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinewall/auth")
public class AuthController {
    private final AuthService authService;
    private final ImageUploadService imageUploadService;

    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(
            @RequestBody @Valid IdCheckRequestDto requestBody
    ){
        ResponseEntity<? super IdCheckResponseDto>response = authService.idCheck(requestBody);
        return response;
    }

    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(
            @RequestBody @Valid EmailCertificationRequestDto requestBody
    ) {
        ResponseEntity<? super EmailCertificationResponseDto> response = authService.emailCertification(requestBody);
        return response;
    }

    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(
            @RequestBody @Valid CheckCertificationRequestDto requestBody
    ){
        ResponseEntity<? super CheckCertificationResponseDto> response = authService.checkCertification(requestBody);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
            @RequestParam("image") MultipartFile image,
            @ModelAttribute @Valid SignUpRequestDto requestBody
    ){
        try {
            // 이미지 업로드
            String imageUrl = imageUploadService.upload(image);  // 이미지를 S3에 업로드하고 URL 반환

            // 실제 회원 가입 처리 (예: 서비스 호출)
            ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody, imageUrl);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }


    @PostMapping("/sign-in")
    public ResponseEntity<?super SignInResponseDto> signIn(
            @RequestBody @Valid SignInRequestDto requestBody,
            HttpServletResponse response
    ){
        return authService.signIn(requestBody, response);

    }

    @PostMapping("/logout")
    public ResponseEntity<? super ResponseDto> logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        request.getSession().invalidate();
        return authService.logout(response);  // 로그아웃 처리
    }
}
