package com.autoever.cinewall.actor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "actor")
public class ActorEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String profilePath;
}
