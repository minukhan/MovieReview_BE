package com.autoever.cinewall.review.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseUserReviewGraph {
    private Map oneStar;
    private Map twoStar;
    private Map threeStar;
    private Map fourStar;
    private Map fiveStar;

    public ResponseUserReviewGraph(int one, int two, int three, int four, int five) {
        this.oneStar = new HashMap();
        oneStar.put("score", "1점");
        oneStar.put("count", one);

        this.twoStar = new HashMap();
        twoStar.put("score", "2점");
        twoStar.put("count", two);

        this.threeStar = new HashMap();
        threeStar.put("score", "3점");
        threeStar.put("count", three);

        this.fourStar = new HashMap();
        fourStar.put("score", "4점");
        fourStar.put("count", four);

        this.fiveStar = new HashMap();
        fiveStar.put("score", "5점");
        fiveStar.put("count", five);
    }
}

