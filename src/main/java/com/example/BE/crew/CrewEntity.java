package com.example.BE.crew;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "crew")
public class CrewEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // profile_path가 null인 경우가 있어서 null이 가능하도록 주석처리
//    @Column(nullable = false)
    private String profilePath;
}
