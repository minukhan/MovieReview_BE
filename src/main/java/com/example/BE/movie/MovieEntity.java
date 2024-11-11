package com.example.BE.movie;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movie")
public class MovieEntity {

    @Id
    private long movieId;

    @Column(nullable = false)
    private String title;

    // overview 데이터가 크기를 넘어가는 경우가 있어서 @Lob 설정을 통해 해결
    // @Lob 설정해도 tinytext 타입으로 생성되는 문제가 있어 타입을 명시적으로 지정해줌
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String overview;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private double voteAverage;

    @Column(nullable = false)
    private int voteCount;

    @Column(nullable = false)
    private String posterPath;

    @Column(nullable = false)
    private String backdropPath;

    @Column(nullable = false)
    private String trailerPath;
}
