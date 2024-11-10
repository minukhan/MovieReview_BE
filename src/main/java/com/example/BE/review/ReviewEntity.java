package com.example.BE.review;


import com.example.BE.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Integer movieId;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating; // double 타입은 고정 소숫점이 안된다고 하여 BigDecimal 사용

    @Column(length = 255)
    private String description;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private java.time.LocalDateTime createDate;
}
