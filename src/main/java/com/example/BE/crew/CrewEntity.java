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
    @Column(name = "crew_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crewId;

    @Column(nullable = false)
    private String name;

  //  @Column(nullable = false)
    @Column
    private String profilePath;
}
