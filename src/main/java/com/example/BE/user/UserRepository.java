package com.example.BE.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findById(String id);
    boolean existsById(String id);
    UserEntity findByUserId(int userId);
    // powerReviewer가 true인 UserEntity 리스트를 반환
    List<UserEntity> findByPowerReviewerTrue();

}
