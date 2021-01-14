package com.example.eatanywhere.network;

import com.example.eatanywhere.model.locations.ApiResponseLocation;
import com.example.eatanywhere.model.restaurants.Location;
import com.example.eatanywhere.model.restaurants.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ZomatoApi {

    @GET("locations")
    Call<ApiResponseLocation> getLocation(@Query("query") String query, @Header("user-key") String userKey);

    // Search for nearby restaurants
    @GET("search")
    Call<ApiResponse> getNearbyRestaurants(@Query("entity_id") int entityId, @Query("entity_type") String entityType,
                                           @Query("count") int count, @Query("radius") double radius, @Header("user-key") String apiKey);
}
