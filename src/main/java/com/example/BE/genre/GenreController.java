package com.example.BE.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@RestController
public class GenreController {





    // application.properties에 tmdb.key 값 설정한 뒤 가져옴
//    @Value("${tmdb.key}")
//    private String apiKey;
//
//    @Autowired
//    private GenreService genreService;
//
//    @GetMapping("/get-genre")
//    public String saveGenre() throws IOException {
//
//        String result = "";  // API 호출 결과를 저장할 문자열 변수 result 초기화
//        // API 호출 URL 생성. apiKey와 페이지 번호 i를 포함하여 API 요청 URL을 구성함
//        String apiURL = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + apiKey + "&language=ko-KR";
//
//        URL url = new URL(apiURL);  // 생성된 API URL을 사용하여 URL 객체를 생성
//
//        BufferedReader bf;
//
//        // URL의 데이터 스트림을 UTF-8 인코딩으로 BufferedReader에 연결하여 API 응답을 읽을 준비를 함
//        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//
//        result = bf.readLine();  // API의 응답 내용 중 한 줄을 읽어 result에 저장
//
//        genreService.saveAllGenre(result);
//
//        return "Success!";
//    }
}
