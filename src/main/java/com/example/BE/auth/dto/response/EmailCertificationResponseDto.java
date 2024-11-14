package com.example.BE.auth.dto.response;


import com.example.BE.auth.common.ResponseCode;
import com.example.BE.auth.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EmailCertificationResponseDto extends ResponseDto {
    private boolean isAvailable; // 이메일 인증 성공 여부

    // 기본 생성자
    private EmailCertificationResponseDto(boolean isAvailable) {
        super();
        this.isAvailable = isAvailable;
    }

    // 이메일 인증 성공 시 응답
    public static ResponseEntity<EmailCertificationResponseDto> success() {
        EmailCertificationResponseDto responseBody = new EmailCertificationResponseDto(true); // 인증 성공
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 중복된 ID가 있을 경우 응답
    public static ResponseEntity<EmailCertificationResponseDto> duplicateId() {
        EmailCertificationResponseDto responseBody = new EmailCertificationResponseDto(false); // 중복 ID
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    // 이메일 발송 실패 시 응답
    public static ResponseEntity<EmailCertificationResponseDto> mailSendFail() {
        EmailCertificationResponseDto responseBody = new EmailCertificationResponseDto(false); // 이메일 발송 실패
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}