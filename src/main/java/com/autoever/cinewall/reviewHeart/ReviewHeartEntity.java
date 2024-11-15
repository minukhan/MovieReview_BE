package com.autoever.cinewall.reviewHeart;

import com.autoever.cinewall.review.ReviewEntity;
import com.autoever.cinewall.user.UserEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewEntity review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
