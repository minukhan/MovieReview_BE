package com.autoever.cinewall.review.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseUserReviewGraph {
    private int oneStar;
    private int twoStar;
    private int threeStar;
    private int fourStar;
    private int fiveStar;

    public ResponseUserReviewGraph(int one, int two, int three, int four, int five) {
        this.oneStar = one;
        this.twoStar = two;
        this.threeStar = three;
        this.fourStar = four;
        this.fiveStar = five;
    }
}

