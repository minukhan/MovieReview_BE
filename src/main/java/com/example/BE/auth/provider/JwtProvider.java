package com.example.BE.auth.provider;
import java.security.Key;
import java.util.Date;

import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${secret-key}")
    private String secretKey;

    private final Key key;

    public JwtProvider(@Value("${secret-key}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String create(String userId){
//        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        UserEntity user = userRepository.findById(userId);
        Claims claims = Jwts.claims().setSubject(user.getId());
        claims.put("roles", user.getRole());
        claims.put("exp", new Date(now.getTime() + 60 * 60 * 1000L));

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(userId)
                .setExpiration(new Date(now.getTime() + 60 * 60 * 1000L))
                .setClaims(claims)
                .compact();

        Jws<Claims> c = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);

        System.out.println(c.getBody());

        return jwt;
    }

    public String validate(String jwt){
        String subject = null;

        try{
            subject =  Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        }catch(Exception exception){
            exception.printStackTrace();
            return null;
        }

        return subject;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    System.out.println("Access Token found in cookies: " + cookie.getValue());  // 로그 추가
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 토큰에서 닉네임만 추출
    public String getUserRole(String token) {
        return (String) Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get("roles");
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
}