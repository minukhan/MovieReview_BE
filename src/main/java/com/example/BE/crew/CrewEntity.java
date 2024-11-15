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
    private int crewId;

    @Column(nullable = false)
    private String name;

    @Column
    private String profilePath;
}
