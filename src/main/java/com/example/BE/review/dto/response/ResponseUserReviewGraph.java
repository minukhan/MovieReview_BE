package com.example.BE.review.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ResponseUserReviewGraph {
    private int one;
    private int two;
    private int three;
    private int four;
    private int five;

    public ResponseUserReviewGraph(int one, int two, int three, int four, int five) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
    }
}

