package com.autoever.cinewall.user;

import com.autoever.cinewall.user.dto.OtherUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findById(String id);
    boolean existsById(String id);
    UserEntity findByUserId(int userId);
    // powerReviewer가 true인 UserEntity 리스트를 반환
    List<UserEntity> findByPowerReviewerTrue();

    @Query("SELECT new com.autoever.cinewall.user.dto.OtherUserDto(" +
            "tu.userId, tu.id, tu.nickname, tu.email, tu.profile_url, tu.powerReviewer, " +
            "EXISTS (SELECT 1 FROM FollowerEntity sub_f WHERE sub_f.toUser = tu AND sub_f.fromUser.userId = :me)) " +
            "FROM UserEntity tu " +
            "WHERE tu.userId = :other")
    OtherUserDto findOtherUser(@Param("me") int me, @Param("other") int other);
}
