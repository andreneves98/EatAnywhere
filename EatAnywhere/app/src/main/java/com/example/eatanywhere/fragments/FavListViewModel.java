/*
    This class is responsible for sharing data between fragments.
 */

package com.example.eatanywhere.fragments;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eatanywhere.model.restaurants.Restaurant_;

import java.util.List;

public class FavListViewModel {
    private final MutableLiveData<List<Restaurant_>> favoriteRestaurants = new MutableLiveData<>();

    public void addFavorite(Restaurant_ restaurant) {
        //favoriteRestaurants.
    }
}
