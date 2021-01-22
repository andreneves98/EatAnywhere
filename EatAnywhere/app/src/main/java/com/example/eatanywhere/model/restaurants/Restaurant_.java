package com.example.eatanywhere.model.restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Restaurant_ implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("cuisines")
    @Expose
    private String cuisines;
    @SerializedName("timings")
    @Expose
    private String timings;
    @SerializedName("average_cost_for_two")
    @Expose
    private int avgCostTwo;
    @SerializedName("price_range")
    @Expose
    private int priceRange;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("user_rating")
    @Expose
    private UserRating userRating;
    @SerializedName("phone_numbers")
    @Expose
    private String phoneNumbers;
    @SerializedName("establishment")
    @Expose
    private List<String> establishment;
    @SerializedName("all_reviews_count")
    @Expose
    private int all_reviews_count;

    public Restaurant_(String id, String name, Location location, String cuisines, String timings,
                       int avgCostTwo, int priceRange, String featuredImage, UserRating userRating,
                       String phoneNumbers, List<String> establishment, int all_reviews_count) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisines = cuisines;
        this.timings = timings;
        this.avgCostTwo = avgCostTwo;
        this.priceRange = priceRange;
        this.featuredImage = featuredImage;
        this.userRating = userRating;
        this.phoneNumbers = phoneNumbers;
        this.establishment = establishment;
        this.all_reviews_count = all_reviews_count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public int getAvgCostTwo() {
        return avgCostTwo;
    }

    public void setAvgCostTwo(int avgCostTwo) {
        this.avgCostTwo = avgCostTwo;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getEstablishment() {
        return establishment;
    }

    public void setEstablishment(List<String> establishment) {
        this.establishment = establishment;
    }

    public int getAll_reviews_count() {
        return all_reviews_count;
    }

    public void setAll_reviews_count(int all_reviews_count) {
        this.all_reviews_count = all_reviews_count;
    }

    @Override
    public String toString() {
        return "Restaurant_{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", cuisines='" + cuisines + '\'' +
                ", timings='" + timings + '\'' +
                ", avgCostTwo=" + avgCostTwo +
                ", priceRange=" + priceRange +
                ", featuredImage='" + featuredImage + '\'' +
                ", userRating=" + userRating +
                ", phoneNumbers='" + phoneNumbers + '\'' +
                ", establishment='" + establishment + '\'' +
                ", all_reviews_count='" + all_reviews_count + '\'' +
                '}';
    }
}

