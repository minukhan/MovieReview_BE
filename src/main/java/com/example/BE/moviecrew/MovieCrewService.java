package com.example.BE.moviecrew;

import com.example.BE.crew.dto.RecommendedCrew;
import com.example.BE.movie.MovieEntity;
import com.example.BE.user.UserEntity;
import com.example.BE.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieCrewService {

    private final MovieCrewRepository movieCrewRepository;
    private final UserRepository userRepository;

    public List<RecommendedCrew> recommendCrew(int userId) throws Exception {
        UserEntity user = userRepository.findByUserId(userId);

        if(user == null) {
            throw new Exception("존재하지 않는 사용자입니다.");
        }

        Pageable topThree = PageRequest.of(0, 3);
        List<RecommendedCrew> recommendedCrews = movieCrewRepository.findCrewByUserId(userId, topThree);

        for(RecommendedCrew crew : recommendedCrews) {
            List<MovieEntity> movieList = movieCrewRepository.findMovieByCrew(crew.getCrewId());
            crew.setMovies(movieList);
        }

        return recommendedCrews;
    }
}
