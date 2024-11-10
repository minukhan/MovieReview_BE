package com.example.BE.certification;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "certification")
public class CertificationEntity {

    @Id
    @Column(nullable = false, length = 255)
    private String userId;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String certificationNumber;
}
