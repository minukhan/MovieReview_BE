package com.example.BE.crew;

import com.example.BE.movie.MovieRepository;
import com.example.BE.moviecrew.MovieCrewEntity;
import com.example.BE.moviecrew.MovieCrewRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Log4j2
@Service
@RequiredArgsConstructor
public class CrewService {

    @Value("${tmdb.key}")
    private String apiKey;

    private final MovieRepository movieRepository;
    private final CrewRepository crewRepository;
    private final MovieCrewRepository movieCrewRepository;

    public void saveCrew(int id) {
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
            list = (JsonArray) jsonObject.get("crew");
            JsonObject contents = null;

            for (int k = 0; k < list.size(); k++) {
                contents = (JsonObject) list.get(k);

                // 스태프 역할 중 Director(감독)에 해당하는 데이터만 저장 -> 추후에 다른 스태프도 필요하면 수정하면 될 듯
                if(contents.get("job").getAsString().equals("Director")) {

                    String profile = contents.get("profile_path").isJsonNull() ? null : ImgUrl + contents.get("profile_path").getAsString();

                    crewRepository.save(CrewEntity.builder()
                            .crewId(contents.get("id").getAsInt())
                            .name(contents.get("name").getAsString())
                            .profilePath(profile)
                            .build());

                    movieCrewRepository.save(MovieCrewEntity.builder()
                            .movie(movieRepository.findById(id).orElseThrow(null))
                            .crew(crewRepository.findById(contents.get("id").getAsLong()).orElse(null))
                            .job(contents.get("job").getAsString())
                            .build());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
