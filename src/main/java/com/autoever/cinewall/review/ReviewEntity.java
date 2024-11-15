package com.autoever.cinewall.review;

import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.reviewHeart.ReviewHeartEntity;
import com.autoever.cinewall.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class ReviewEntity {

    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating; // double 타입은 고정 소숫점이 안된다고 하여 BigDecimal 사용

    @Column(length = 255)
    private String description;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private java.time.LocalDateTime createDate;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewHeartEntity> hearts = new ArrayList<>();

    public int getReviewHeartCount() {
        return hearts.size();
    }
}
