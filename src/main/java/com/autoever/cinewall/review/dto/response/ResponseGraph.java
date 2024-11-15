package com.autoever.cinewall.review.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseGraph {
    @JsonProperty("score")
    private String score;

    @JsonProperty("count")
    private int count;

    public ResponseGraph(String score, int count) {
        this.score = score;
        this.count = count;
    }
}
