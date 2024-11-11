package com.example.BE.actor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "actor")
public class ActorEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 없는 경우가 있어서 주석처리
//    @Column(nullable = false)
    private String profilePath;
}
