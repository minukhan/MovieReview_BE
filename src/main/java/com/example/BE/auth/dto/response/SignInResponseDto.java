package com.example.BE.auth.dto.response;

import com.example.BE.auth.common.ResponseCode;
import com.example.BE.auth.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.example.BE.auth.common.ResponseCode;
import com.example.BE.auth.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {
    private String message;

    private SignInResponseDto(String message) {
        super();
        this.message = message;
    }

    // 로그인 성공 시 응답 (쿠키에 토큰 저장)
    public static ResponseEntity<SignInResponseDto> success() {
        SignInResponseDto responseBody = new SignInResponseDto(ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 로그인 실패 시 응답
    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}