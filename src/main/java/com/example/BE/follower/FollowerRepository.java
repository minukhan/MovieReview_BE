package com.example.BE.follower;

import com.example.BE.follower.dto.response.FollowerResponseDto;
import com.example.BE.follower.dto.response.FollowingResponseDto;
import com.example.BE.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowerRepository extends JpaRepository<FollowerEntity, Integer> {

    @Query("SELECT new com.example.BE.follower.dto.response.FollowingResponseDto(f.followerId, f.toUser.userId, f.toUser.nickname, f.toUser.profile_url) " +
            "FROM FollowerEntity f " +
            "WHERE f.fromUser.userId = :userId")
    List<FollowingResponseDto> findFollowingByUserId(@Param("userId") int userId);

    @Query("SELECT new com.example.BE.follower.dto.response.FollowerResponseDto(f.followerId, f.fromUser.userId, f.fromUser.nickname, f.fromUser.profile_url) " +
            "FROM FollowerEntity f " +
            "WHERE f.toUser.userId = :userId")
    List<FollowerResponseDto> findFollowerByUserId(@Param("userId") int userId);
}
