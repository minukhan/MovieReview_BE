package com.autoever.cinewall.auth.controller;

import com.autoever.cinewall.auth.dto.request.*;
import com.autoever.cinewall.auth.dto.response.*;
import com.autoever.cinewall.auth.service.AuthService;
import com.autoever.cinewall.s3.service.ImageUploadService;
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
            String imageUrl = imageUploadService.upload(image);

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
        return authService.logout(response);
    }
}
