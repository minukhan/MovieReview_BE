package com.example.BE.follower;

import com.example.BE.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "follower")
public class FollowerEntity {

    @Id
    @Column(name="follower_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int followerId;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private UserEntity fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private UserEntity toUser;
}
