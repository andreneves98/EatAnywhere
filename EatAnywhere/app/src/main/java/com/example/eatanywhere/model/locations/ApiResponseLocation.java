package com.example.eatanywhere.model.locations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseLocation {
    @SerializedName("location_suggestions")
    private List<LocationSuggestion> locationSuggestions;
    @SerializedName("status")
    private String status;
    @SerializedName("has_more")
    private int hasMore;
    @SerializedName("has_total")
    private int hasTotal;
    @SerializedName("user_has_addresses")
    private boolean userHasAddresses;

    public List<LocationSuggestion> getLocationSuggestions() {
        return locationSuggestions;
    }

    public void setLocationSuggestions(List<LocationSuggestion> locationSuggestions) {
        this.locationSuggestions = locationSuggestions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public int getHasTotal() {
        return hasTotal;
    }

    public void setHasTotal(int hasTotal) {
        this.hasTotal = hasTotal;
    }

    public boolean isUserHasAddresses() {
        return userHasAddresses;
    }

    public void setUserHasAddresses(boolean userHasAddresses) {
        this.userHasAddresses = userHasAddresses;
    }
}
