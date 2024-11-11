package com.example.BE.genre;

import com.example.BE.movie.MovieEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Log4j2
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    // 초기에 장르 데이터를 모두 불러와서 저장하기 위한 메서드
    public String saveAllGenre(String result) {
        // 장르 데이터는 json에서 genres라는 키 값에 리스트로 존재하기 때문에 이를 담기 위한 JsonArray 선언
        JsonArray list = null;

        log.info("서비스 시작" );
        JsonParser jsonParser = new JsonParser();  // JSON 문자열을 파싱하기 위한 JsonParser 객체 생성
        JsonObject jsonObject = (JsonObject) jsonParser.parse(result);  // result 문자열을 JSON 형식으로 파싱하여 JsonObject로 변환
        list = (JsonArray) jsonObject.get("genres");  // jsonObject에서 "genres" 키에 해당하는 값을 JsonArray 타입으로 가져옴
        JsonObject contents = null;  // JsonObject 타입의 contents 변수를 선언하고 초기화 (값은 리스트를 돌면서 넣어줄 예정)

        // JsonArray를 순회하면서 각 데이터들을 데이터베이스에 저장
        for (int k = 0; k < list.size(); k++) {
            contents = (JsonObject) list.get(k);

            log.info("id : " + contents.get("id") + ", name : " + contents.get("name"));

            // Genre 데이터 저장
            genreRepository.save(
                    GenreEntity.builder()
                            .id(contents.get("id").getAsLong())
                            .name(contents.get("name").getAsString().replaceAll("\"", ""))
                            .build()
            );
        }

        return "Success!";
    }
}
