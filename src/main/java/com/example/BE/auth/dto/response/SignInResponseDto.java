package com.example.BE.auth.dto.response;

import com.example.BE.auth.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {
    private boolean isAvailable; // 로그인 성공 여부
    private String message; // 메시지

    // 기본 생성자
    private SignInResponseDto(boolean isAvailable, String message) {
        super();
        this.isAvailable = isAvailable;
        this.message = message;
    }

    // 로그인 성공 시 응답 (쿠키에 토큰 저장)
    public static ResponseEntity<SignInResponseDto> success() {
        SignInResponseDto responseBody = new SignInResponseDto(true, ResponseMessage.SUCCESS); // 성공
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 로그인 실패 시 응답
    public static ResponseEntity<SignInResponseDto> signInFail() {
        SignInResponseDto responseBody = new SignInResponseDto(false, ResponseMessage.SIGN_IN_FAIL); // 실패
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}