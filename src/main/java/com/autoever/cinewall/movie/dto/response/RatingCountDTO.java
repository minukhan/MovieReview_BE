package com.autoever.cinewall.movie.dto.response;

public class RatingCountDTO {
    private int score;
    private long count;

    public RatingCountDTO(int score, long count) {
        this.score = score;
        this.count = count;
    }

    public int getScore() {
        return score;
    }

    public long getCount() {
        return count;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCount(long count) {
        this.count = count;
    }
}