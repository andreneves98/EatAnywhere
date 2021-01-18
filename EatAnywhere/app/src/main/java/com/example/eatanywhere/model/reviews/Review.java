package com.example.eatanywhere.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review implements Serializable {

    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("review_text")
    @Expose
    private String reviewText;
    @SerializedName("id")
    @Expose
    private int reviewId;
    @SerializedName("rating_color")
    @Expose
    private String ratingColor;
    @SerializedName("review_time_friendly")
    @Expose
    private String reviewTimeFriendly;
    @SerializedName("rating_text")
    @Expose
    private String ratingText;
    @SerializedName("timestamp")
    @Expose
    private int timestamp;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("comments_count")
    @Expose
    private int commentsCount;

    public Review(int rating, String reviewText, int reviewId, String ratingColor,
                  String reviewTimeFriendly, String ratingText, int timestamp, int likes,
                  User user, int commentsCount) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewId = reviewId;
        this.ratingColor = ratingColor;
        this.reviewTimeFriendly = reviewTimeFriendly;
        this.ratingText = ratingText;
        this.timestamp = timestamp;
        this.likes = likes;
        this.user = user;
        this.commentsCount = commentsCount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public String getReviewTimeFriendly() {
        return reviewTimeFriendly;
    }

    public void setReviewTimeFriendly(String reviewTimeFriendly) {
        this.reviewTimeFriendly = reviewTimeFriendly;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public String timestampToDate(int timestamp) {
        return new SimpleDateFormat("dd-mm-yyyy")
                .format(new Date(this.timestamp * 1000L));
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
