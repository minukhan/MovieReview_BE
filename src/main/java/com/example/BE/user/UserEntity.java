package com.example.BE.user;
import com.example.BE.auth.dto.request.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean survey;

    @Column(nullable = false)
    private String profile_url;

    @Column(nullable = false)
    private boolean powerReviewer;

    //일반 회원가입을 위한 생성자
    public UserEntity(SignUpRequestDto dto, String profile_url){
        this.id = dto.getId();
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.type = "app";
        this.role = "ROLE_USER";
        this.survey = false;
        this.profile_url = profile_url;
        this.powerReviewer = false;
    }

//카카오 로그인을 위한 생성자
    public UserEntity(String id, String nickname, String email, String type, String profile_url){
        this.id = id;
        this.nickname = nickname;
        this.password = "passw0rd"; //의미 없기 때문
        this.email = email;
        this.type = type;
        this.role = "ROLE_USER";
        this.survey = false;
        this.profile_url = profile_url;
        this.powerReviewer = false;
    }
}
