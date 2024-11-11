package com.example.BE.movie;

import com.example.BE.actor.ActorService;
import com.example.BE.crew.CrewService;
import com.example.BE.genre.GenreService;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
@RestController
public class MovieController {

    // application.properties에 tmdb.key 값 설정한 뒤 가져옴
    @Value("${tmdb.key}")
    private String apiKey;

    @Autowired
    private MovieService movieService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private CrewService crewService;
    @Autowired
    private GenreService genreService;

    @GetMapping("/get-initial-data")
    public String getMovieData() {

        String ok = "";

        try {
            // 원래는 영화가 페이지마다 20개씩 넘어오는데 우리 조는 트레일러 영상이 있는 데이터만 사용하기 때문에
            // for문을 통해 적절히 반복하면서 데이터 갯수 조절할 예정(i 범위 조정)
            for (int i = 1; i <= 30; i++) {
                // log.info("##########################" + i + "번째 cycle ##############################");
                String result = "";  // API 호출 결과를 저장할 문자열 변수 result 초기화
                // API 호출 URL 생성. apiKey와 페이지 번호 i를 포함하여 API 요청 URL을 구성함
                String apiURL = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey
                        + "&release_date.gte=2013-01-01&watch_region=KR&language=ko&page=" + i;

                URL url = new URL(apiURL);  // 생성된 API URL을 사용하여 URL 객체를 생성

                BufferedReader bf;

                // URL의 데이터 스트림을 UTF-8 인코딩으로 BufferedReader에 연결하여 API 응답을 읽을 준비를 함
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

                result = bf.readLine();  // API의 응답 내용 중 한 줄을 읽어 result에 저장

                ok = movieService.saveInitialData(result);  // API 호출 결과 result를 movieService의 saveInitialData 메서드에 전달하여 초기 데이터를 저장
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ok;
    }


}
