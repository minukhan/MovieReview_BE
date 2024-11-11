package com.example.BE.follower;

import com.example.BE.follower.dto.response.FollowingResponseDto;
import com.example.BE.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowerRepository extends JpaRepository<FollowerEntity, Integer> {

    @Query("SELECT f.toUser " +
            "FROM FollowerEntity f " +
            "WHERE f.fromUser.userId = :userId")
    List<UserEntity> findFollowingByUserId(@Param("userId") int userId);
}
