package com.autoever.cinewall.actor;

import com.autoever.cinewall.actor.dto.RecommendedActor;
import com.autoever.cinewall.movie.MovieEntity;
import com.autoever.cinewall.movie.MovieRepository;
import com.autoever.cinewall.movieactor.MovieActorEntity;
import com.autoever.cinewall.movieactor.MovieActorRepository;
import com.autoever.cinewall.user.UserEntity;
import com.autoever.cinewall.user.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ActorService {

    @Value("${tmdb.key}")
    private String apiKey;

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieActorRepository movieActorRepository;
    private final UserRepository userRepository;

    public List<RecommendedActor> recommendActor(int userId) throws Exception {

        UserEntity user = userRepository.findByUserId(userId);

        if (user == null){
            throw new Exception("존재하지 않는 사용자입니다.");
        }

        Pageable topThree = PageRequest.of(0, 3);
        List<RecommendedActor> recommendedActors = actorRepository.findRecommendedActor(userId, topThree);

        for(RecommendedActor actor : recommendedActors) {
            List<MovieEntity> movieList = movieActorRepository.findMovieByActor(actor.getActorId());
            actor.setMovies(movieList);
        }

        return recommendedActors;
    }

    public void saveActor(Integer id) throws MalformedURLException {

        try{
            String result = "";
            String apiURL = "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=" + apiKey + "&language=ko";
            String ImgUrl = "https://image.tmdb.org/t/p/w200";

            URL url = new URL(apiURL);

            BufferedReader bf;

            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            result = bf.readLine();

            JsonArray list = null;
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
            list = (JsonArray) jsonObject.get("cast");  // cast는 배우 명단, crew는 스태프 명단
            JsonObject contents = null;

            for (int k = 0; k < list.size(); k++) {
                contents = (JsonObject) list.get(k);

                if(contents.get("character").getAsString().contains("(uncredited)") || contents.get("character").getAsString().contains("(voice)")){
                    continue;
                }
                if(contents.get("order").getAsInt() > 9) break;
                // profile_path 가 null인 경우가 있어서 이 경우 null 값을 넣도록 처리(없으면 Exception 처리됨)
                // 이 null 값 대신 추후에 default 이미지 같은 거 넣어주면 될 것 같습니다
                String profile = contents.get("profile_path").isJsonNull() ? null : ImgUrl + contents.get("profile_path").getAsString();

//                log.info("id : " + contents.get("id").getAsInt() + ", name : " + contents.get("name").getAsString() + ", profilePath : " + profile + ", characterName : " + contents.get("character").getAsString());

                actorRepository.save(ActorEntity.builder()
                        .id(contents.get("id").getAsLong())
                        .name(contents.get("name").getAsString())
                        .profilePath(profile)
                        .build());

                movieActorRepository.save(MovieActorEntity.builder()
                        .movie(movieRepository.findById(id).orElse(null))
                        .actor(actorRepository.findById(contents.get("id").getAsLong()).orElse(null))
                        .characterName(contents.get("character").getAsString())
                        .build());

                log.info("actor 성공 !");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
