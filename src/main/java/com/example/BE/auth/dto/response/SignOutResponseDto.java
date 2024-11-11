package com.example.BE.auth.dto.response;

import com.example.BE.auth.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignOutResponseDto extends ResponseDto {

    private String message;

    public SignOutResponseDto(String message) {
        super();
        this.message = message;
    }

    // 로그아웃 성공 응답
    public static ResponseEntity<SignOutResponseDto> success() {
        SignOutResponseDto responseBody = new SignOutResponseDto(ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}