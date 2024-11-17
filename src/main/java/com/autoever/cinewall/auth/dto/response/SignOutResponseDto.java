package com.autoever.cinewall.auth.dto.response;

import com.autoever.cinewall.auth.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignOutResponseDto extends ResponseDto {
    private boolean isAvailable; // 로그아웃 성공 여부
    private String message;

    // 기본 생성자
    private SignOutResponseDto(boolean isAvailable, String message) {
        super();
        this.isAvailable = isAvailable;
        this.message = message;
    }

    // 로그아웃 성공 시 응답
    public static ResponseEntity<SignOutResponseDto> success() {
        SignOutResponseDto responseBody = new SignOutResponseDto(true, ResponseMessage.SUCCESS); // 로그아웃 성공
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 로그아웃 실패 시 응답
    public static ResponseEntity<SignOutResponseDto> fail() {
        SignOutResponseDto responseBody = new SignOutResponseDto(false, ResponseMessage.SIGN_OUT_FAIL); // 로그아웃 실패
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}