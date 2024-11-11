package com.example.BE.reviewHeart;

import com.example.BE.review.ReviewEntity;
import com.example.BE.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reviewHeart")
public class ReviewHeartEntity {

    @Id
    @Column(name="revewHeart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewHeart_id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
