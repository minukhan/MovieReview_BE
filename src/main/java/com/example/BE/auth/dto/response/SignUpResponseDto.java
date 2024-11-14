package com.example.BE.auth.dto.response;


import com.example.BE.auth.common.ResponseCode;
import com.example.BE.auth.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponseDto extends ResponseDto {
    private boolean isAvailable; // ID 사용 가능 여부

    // 기본 생성자
    private SignUpResponseDto(boolean isAvailable) {
        super();
        this.isAvailable = isAvailable;
    }

    // 회원가입 성공 시 응답 (ID가 사용 가능한 경우)
    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto responseBody = new SignUpResponseDto(true); // ID 사용 가능
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 중복된 ID가 있을 경우 응답
    public static ResponseEntity<SignUpResponseDto> duplicateId() {
        SignUpResponseDto responseBody = new SignUpResponseDto(false); // ID 중복
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    // 인증 실패 시 응답
    public static ResponseEntity<SignUpResponseDto> certificationFail() {
        SignUpResponseDto responseBody = new SignUpResponseDto(false); // 인증 실패
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}