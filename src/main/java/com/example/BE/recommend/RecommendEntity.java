package com.example.BE.recommend;

import com.example.BE.genre.GenreEntity;
import com.example.BE.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recommend")
public class RecommendEntity {

    @Id
    @Column(name="recommend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommend_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "recommend_genres",
            joinColumns = @JoinColumn(name = "recommend_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<GenreEntity> genres;
}
