package com.example.eatanywhere.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("zomato_handle")
    @Expose
    private String zomatoHandle;
    @SerializedName("foodie_level")
    @Expose
    private String foodieLevel;
    @SerializedName("foodie_level_num")
    @Expose
    private int foodieLevelNum;
    @SerializedName("foodie_color")
    @Expose
    private String foodieColor;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("profile_deeplink")
    @Expose
    private String profileDeeplink;

    public User(String name, String zomatoHandle, String foodieLevel, int foodieLevelNum,
                String foodieColor, String profileUrl, String profileImage, String profileDeeplink) {
        this.name = name;
        this.zomatoHandle = zomatoHandle;
        this.foodieLevel = foodieLevel;
        this.foodieLevelNum = foodieLevelNum;
        this.foodieColor = foodieColor;
        this.profileUrl = profileUrl;
        this.profileImage = profileImage;
        this.profileDeeplink = profileDeeplink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZomatoHandle() {
        return zomatoHandle;
    }

    public void setZomatoHandle(String zomatoHandle) {
        this.zomatoHandle = zomatoHandle;
    }

    public String getFoodieLevel() {
        return foodieLevel;
    }

    public void setFoodieLevel(String foodieLevel) {
        this.foodieLevel = foodieLevel;
    }

    public int getFoodieLevelNum() {
        return foodieLevelNum;
    }

    public void setFoodieLevelNum(int foodieLevelNum) {
        this.foodieLevelNum = foodieLevelNum;
    }

    public String getFoodieColor() {
        return foodieColor;
    }

    public void setFoodieColor(String foodieColor) {
        this.foodieColor = foodieColor;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileDeeplink() {
        return profileDeeplink;
    }

    public void setProfileDeeplink(String profileDeeplink) {
        this.profileDeeplink = profileDeeplink;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", zomatoHandle='" + zomatoHandle + '\'' +
                ", foodieLevel='" + foodieLevel + '\'' +
                ", foodieLevelNum=" + foodieLevelNum +
                ", foodieColor='" + foodieColor + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", profileDeeplink='" + profileDeeplink + '\'' +
                '}';
    }
}
