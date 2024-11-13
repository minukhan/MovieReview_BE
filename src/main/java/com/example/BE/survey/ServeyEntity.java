package com.example.BE.survey;

import com.example.BE.genre.GenreEntity;
import com.example.BE.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "survey")
public class ServeyEntity{

    @Id
    @Column(name = "survey_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int surveyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genre;
}
