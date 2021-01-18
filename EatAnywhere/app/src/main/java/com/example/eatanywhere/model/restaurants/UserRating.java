package com.example.eatanywhere.model.restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserRating implements Serializable {

    @SerializedName("aggregate_rating")
    @Expose
    private String aggregateRating;
    @SerializedName("rating_text")
    @Expose
    private String ratingText;
    @SerializedName("rating_color")
    @Expose
    private String ratingColor;
    @SerializedName("votes")
    @Expose
    private Integer votes;

    public UserRating(String aggregateRating, String ratingText, String ratingColor, Integer votes) {
        this.aggregateRating = aggregateRating;
        this.ratingText = ratingText;
        this.ratingColor = ratingColor;
        this.votes = votes;
    }

    public String getAggregateRating() {
        return aggregateRating;
    }

    public void setAggregateRating(String aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "UserRating{" +
                "aggregateRating='" + aggregateRating + '\'' +
                ", ratingText='" + ratingText + '\'' +
                ", ratingColor='" + ratingColor + '\'' +
                ", votes=" + votes +
                '}';
    }
}
