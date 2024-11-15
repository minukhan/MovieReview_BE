package com.autoever.cinewall.movie.dto.response;

public class RatingCountDTO {
    private String score;
    private long count;

    public RatingCountDTO(String score, long count) {
        this.score = score;
        this.count = count;
    }

    public String getScore() {
        return score;
    }

    public long getCount() {
        return count;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setCount(long count) {
        this.count = count;
    }
}