package com.example.BE.user;
import com.example.BE.auth.dto.request.SignUpRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {

    @Id
    private String userId;

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
        this.userId = dto.getId();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.type = "app";
        this.role = "ROLE_USER";
        this.survey = false;
    }

//카카오 로그인을 위한 생성자
    public UserEntity(String userId, String email, String type){
        this.userId = userId;
        this.password = "passw0rd"; //의미 없기 때문
        this.email = email;
        this.type = type;
        this.role = "ROLE_USER";
        this.survey = false;
    }
}
