package com.example.BE.auth.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class IdCheckResponseDto extends ResponseDto{
    private boolean isAvailable; // ID가 사용 가능한지 여부를 나타내는 필드

    // 기본 생성자
    private IdCheckResponseDto(boolean isAvailable) {
        super();
        this.isAvailable = isAvailable;
    }

    // 성공적인 응답 (사용 가능한 ID)
    public static ResponseEntity<IdCheckResponseDto> success() {
        IdCheckResponseDto responseBody = new IdCheckResponseDto(true); // 사용 가능
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 중복된 ID (사용 불가능)
    public static ResponseEntity<IdCheckResponseDto> duplicateId() {
        IdCheckResponseDto responseBody = new IdCheckResponseDto(false); // 사용 불가능
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}