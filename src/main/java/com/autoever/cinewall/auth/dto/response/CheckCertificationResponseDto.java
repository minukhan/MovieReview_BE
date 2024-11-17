package com.autoever.cinewall.auth.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CheckCertificationResponseDto extends ResponseDto {
    private boolean isAvailable; // 인증 성공 여부

    // 기본 생성자
    private CheckCertificationResponseDto(boolean isAvailable) {
        super();
        this.isAvailable = isAvailable;
    }

    // 인증 성공 시 응답
    public static ResponseEntity<CheckCertificationResponseDto> success() {
        CheckCertificationResponseDto responseBody = new CheckCertificationResponseDto(true); // 인증 성공
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 인증 실패 시 응답
    public static ResponseEntity<CheckCertificationResponseDto> certificationFail() {
        CheckCertificationResponseDto responseBody = new CheckCertificationResponseDto(false); // 인증 실패
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}