package com.example.BE.recommend.service.implement;

import com.example.BE.recommend.RecommendEntity;
import com.example.BE.recommend.RecommendRepository;
import com.example.BE.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendServiceImplement implements RecommendService {
    private final RecommendRepository recommendRepository;
    public void submit_recommend(RecommendEntity recommend) {
        recommendRepository.save(recommend);
    }
}
