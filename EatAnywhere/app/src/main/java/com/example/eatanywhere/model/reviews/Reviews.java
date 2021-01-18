package com.example.eatanywhere.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Reviews implements Serializable {

    @SerializedName("reviews_count")
    @Expose
    private int reviewsCount;
    @SerializedName("reviews_start")
    @Expose
    private int reviewsStart;
    @SerializedName("reviews_shown")
    @Expose
    private int reviewsShown;
    @SerializedName("user_reviews")
    @Expose
    private List<UserReview> userReviews;

    public Reviews(int reviewsCount, int reviewsStart, int reviewsShown, List<UserReview> userReviews) {
        this.reviewsCount = reviewsCount;
        this.reviewsStart = reviewsStart;
        this.reviewsShown = reviewsShown;
        this.userReviews = userReviews;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public int getReviewsStart() {
        return reviewsStart;
    }

    public void setReviewsStart(int reviewsStart) {
        this.reviewsStart = reviewsStart;
    }

    public int getReviewsShown() {
        return reviewsShown;
    }

    public void setReviewsShown(int reviewsShown) {
        this.reviewsShown = reviewsShown;
    }

    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<UserReview> userReviews) {
        this.userReviews = userReviews;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "reviewsCount=" + reviewsCount +
                ", reviewsStart=" + reviewsStart +
                ", reviewsShown=" + reviewsShown +
                ", userReviews=" + userReviews +
                '}';
    }
}
