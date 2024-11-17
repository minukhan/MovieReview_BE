package com.autoever.cinewall.recommend.service.implement;

import com.autoever.cinewall.genre.GenreEntity;
import com.autoever.cinewall.recommend.RecommendEntity;
import com.autoever.cinewall.recommend.RecommendRepository;
import com.autoever.cinewall.recommend.dto.response.RecommendResponseDto;
import com.autoever.cinewall.recommend.service.RecommendService;
import com.autoever.cinewall.user.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImplement implements RecommendService {
    private final RecommendRepository recommendRepository;
    public void submit_recommend(RecommendEntity recommend) {
        recommendRepository.save(recommend);
    }

    @Override
    public ResponseEntity<RecommendResponseDto> getHashtag(UserEntity user) {
        RecommendEntity recommend = recommendRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Recommend not found"));

        List<GenreEntity> genres = recommend.getGenres();
        RecommendResponseDto responseDto = RecommendResponseDto.builder()
                .genre1(genres.get(0).getName())
                .genre2(genres.get(1).getName())
                .genre3(genres.get(2).getName())
                .genre4(genres.get(3).getName())
                .genre5(genres.get(4).getName())
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
