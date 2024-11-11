package com.example.BE.moviecrew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCrewRepository extends JpaRepository<MovieCrewEntity, Long> {
}
