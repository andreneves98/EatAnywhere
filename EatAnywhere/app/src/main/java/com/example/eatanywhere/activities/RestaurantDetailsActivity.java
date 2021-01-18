package com.example.eatanywhere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.example.eatanywhere.R;
import com.example.eatanywhere.model.restaurants.Restaurant_;
import com.example.eatanywhere.model.reviews.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity {

    Intent intent;
    Restaurant_ restaurant;
    List<Review> reviews;

    ImageView thumb;
    TextView restaurantName;
    TextView rating;
    TextView reviewsCount;
    TextView priceCategory;
    TextView location;
    TextView schedule;
    TextView phoneNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        thumb = findViewById(R.id.thumb);
        restaurantName = findViewById(R.id.restaurant_name);
        rating = findViewById(R.id.rating);
        reviewsCount = findViewById(R.id.reviews_count);
        priceCategory = findViewById(R.id.price_category);
        location = findViewById(R.id.location);
        //schedule = findViewById(R.id.schedule);
        phoneNumbers = findViewById(R.id.phone_numbers);

        intent = getIntent();
        restaurant = (Restaurant_) intent.getSerializableExtra("restaurant");
        //reviews = (ArrayList<Review>) intent.getParcelableArrayListExtra("reviews");

        String reviewListAsString = intent.getStringExtra("reviews");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Review>>(){}.getType();
        reviews = gson.fromJson(reviewListAsString, type);

        generatePage(restaurant);
    }

    private void generatePage(Restaurant_ restaurant){
        Picasso.with(this).load(restaurant.getFeaturedImage()).into(thumb);
        restaurantName.setText(restaurant.getName());
        rating.setText(restaurant.getUserRating().getAggregateRating());
        String ratingColor = "#" + restaurant.getUserRating().getRatingColor();
        rating.setTextColor(Color.parseColor(ratingColor));
        // reviewsCount.setText(restaurant.); falta fazer a query para as reviews...

        String price_category = processPriceCategory(restaurant.getPriceRange(), restaurant.getCuisines());
        priceCategory.setText(price_category);
        location.setText(restaurant.getLocation().getAddress());
        //schedule.setText(restaurant.getTimings());

        String phone_numbers;
        try {
            String[] phoneNumbersMult = restaurant.getPhoneNumbers().split(",");
            phone_numbers = phoneNumbersMult[0];
        } catch(Exception e) {
            phone_numbers = restaurant.getPhoneNumbers();
        }

        phoneNumbers.setText(phone_numbers);

    }

    private String processPriceCategory(int price_range, String cuisines) {
        String price_category = "";
        switch(price_range) {
            case 2:
                price_category = "€";
                break;
            case 3:
                price_category = "€€";
                break;
            case 4:
                price_category = "€€€";
                break;
        }
        price_category += " | " + cuisines;

        return price_category;
    }

}