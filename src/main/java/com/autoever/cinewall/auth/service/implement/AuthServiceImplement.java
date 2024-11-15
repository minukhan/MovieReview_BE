package com.autoever.cinewall.auth.service.implement;

import com.autoever.cinewall.auth.dto.request.*;
import com.autoever.cinewall.auth.dto.response.*;
import com.autoever.cinewall.auth.common.CertificationNumber;
import com.autoever.cinewall.auth.common.ResponseCode;
import com.autoever.cinewall.auth.common.ResponseMessage;
import com.example.BE.auth.dto.request.*;
import com.example.BE.auth.dto.response.*;
import com.autoever.cinewall.auth.provider.EmailProvider;
import com.autoever.cinewall.auth.provider.JwtProvider;
import com.autoever.cinewall.auth.service.AuthService;
import com.autoever.cinewall.certification.CertificationEntity;
import com.autoever.cinewall.certification.CertificationRepository;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try {
            String id = dto.getId();
            boolean isExistId = userRepository.existsById(id);
            if(isExistId) return IdCheckResponseDto.duplicateId();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(
            EmailCertificationRequestDto dto) {
        try {
            String id = dto.getId();
            String email = dto.getEmail();

            boolean isExistId = userRepository.existsById(id);
            if(isExistId) return EmailCertificationResponseDto.duplicateId();

            String certificationNumber = CertificationNumber.getCertificationNumber();
            boolean isSuccessed = emailProvider.sendCerfiticationMail(email, certificationNumber);
            if(!isSuccessed) return EmailCertificationResponseDto.mailSendFail();

            CertificationEntity certificationEntity = new CertificationEntity(id, email, certificationNumber);
            certificationRepository.save(certificationEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(
            CheckCertificationRequestDto dto) {
        try {
            String id = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            Optional<CertificationEntity> optionalCertification = certificationRepository.findById(id);

            // 인증 데이터가 없을 경우 실패 응답 반환
            if (optionalCertification.isEmpty()) {
                return CheckCertificationResponseDto.certificationFail();
            }

            // Optional에서 실제 엔티티 추출
            CertificationEntity certificationEntity = optionalCertification.get();

            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);

            if(!isMatched) return CheckCertificationResponseDto.certificationFail();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto, String profile_url) {
        try {
            String id = dto.getId();
            boolean isExistId = userRepository.existsById(id);
            if(isExistId) return SignUpResponseDto.duplicateId();

            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            Optional<CertificationEntity> optionalCertification = certificationRepository.findById(id);

            // 인증 데이터가 없을 경우 실패 응답 반환
            if (optionalCertification.isEmpty()) {
                return SignUpResponseDto.certificationFail();
            }

            // Optional에서 실제 엔티티 추출
            CertificationEntity certificationEntity = optionalCertification.get();
            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);

            if(!isMatched) return SignUpResponseDto.certificationFail();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);

            dto.setPassword(encodedPassword);
            UserEntity userEntity = new UserEntity(dto, profile_url);
            userRepository.save(userEntity);

            certificationRepository.deleteById(id);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto, HttpServletResponse response) {
        String token;
        try {
            String id = dto.getId();
            UserEntity userEntity = userRepository.findById(id);

            // 사용자 존재 여부 확인
            if (userEntity == null) return SignInResponseDto.signInFail();

            // 비밀번호 검증
            String password = dto.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFail();

            // JWT 토큰 생성
            token = jwtProvider.create(id);

            response.addHeader("Authorization", "Bearer " + token);

            // 발급된 토큰을 쿠키에 저장
//            Cookie cookie = new Cookie("accessToken", token);
//            cookie.setHttpOnly(true);// HTTPS 환경에서만 전송
//            cookie.setPath("/");
//            cookie.setMaxAge(7 * 24 * 60 * 60); // 7일간 유효

//            response.addCookie(cookie);

            // SameSite 속성 추가 - 헤더 방식 사용
//            response.addHeader("Set-Cookie", cookie.getName() + "=" + cookie.getValue()
//                    + "; Path=" + cookie.getPath()
//                    + "; Max-Age=" + cookie.getMaxAge()
//                    + "; HttpOnly; SameSite=None");

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        // 로그인 성공 응답
        return SignInResponseDto.success();
    }

    // 로그아웃 처리 메서드
    public ResponseEntity<? super ResponseDto> logout(HttpServletResponse response) {
        try {
            // accessToken 쿠키 삭제
            Cookie cookie = new Cookie("accessToken", null);
            cookie.setHttpOnly(true);  // JS에서 접근 불가
            cookie.setSecure(true);    // HTTPS 환경에서만 전송
            cookie.setPath("/");
            cookie.setMaxAge(0);       // 쿠키 만료 시간 0으로 설정

            response.addCookie(cookie); // 쿠키 삭제
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        // 로그아웃 성공 응답
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS));
    }

}