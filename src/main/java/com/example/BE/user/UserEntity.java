package com.example.BE.user;
import com.example.BE.auth.dto.request.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String id;

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

    //일반 회원가입을 위한 생성자
    public UserEntity(SignUpRequestDto dto){
        this.id = dto.getId();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.type = "app";
        this.role = "ROLE_USER";
        this.survey = false;
    }

//카카오 로그인을 위한 생성자
    public UserEntity(String userId, String email, String type){
        this.id = userId;
        this.password = "passw0rd"; //의미 없기 때문
        this.email = email;
        this.type = type;
        this.role = "ROLE_USER";
        this.survey = false;
    }
}
