package com.example.eatanywhere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatanywhere.R;
import com.example.eatanywhere.adapter.ReviewsAdapter;
import com.example.eatanywhere.model.restaurants.Restaurant;
import com.example.eatanywhere.model.restaurants.Restaurant_;
import com.example.eatanywhere.model.reviews.Review;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RestaurantDetailsActivity extends AppCompatActivity {

    Intent intent;
    Restaurant_ restaurant;
    List<Review> reviews;
    RecyclerView recyclerView;
    ReviewsAdapter adapter;

    ImageView thumb;
    TextView restaurantName;
    TextView rating;
    TextView reviewsCount;
    TextView priceCategory;
    TextView location;
    FirebaseUser user;
    TextView schedule;
    TextView phoneNumbers;
    String latitude, longitude, address;
    FloatingActionButton favButton;
    ProgressBar progressBar;
    private List<Restaurant_> favRestaurantsList;
    boolean fav_checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        thumb = findViewById(R.id.thumbFav);
        restaurantName = findViewById(R.id.restaurant_name);
        rating = findViewById(R.id.rating);
        reviewsCount = findViewById(R.id.reviews_count);
        priceCategory = findViewById(R.id.price_category);
        location = findViewById(R.id.location);
        //schedule = findViewById(R.id.schedule);
        user = FirebaseAuth.getInstance().getCurrentUser();
        phoneNumbers = findViewById(R.id.phone_numbers);

        intent = getIntent();
        restaurant = (Restaurant_) intent.getSerializableExtra("restaurant");
        //reviews = (ArrayList<Review>) intent.getParcelableArrayListExtra("reviews");

        String reviewListAsString = intent.getStringExtra("reviews");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Review>>(){}.getType();
        reviews = gson.fromJson(reviewListAsString, type);

        generatePage(restaurant, reviews);

        favButton=findViewById(R.id.fav_button);
        progressBar=findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);





    }

    public void onClickFav(View v){

        System.out.println("  Restaurante==      "+restaurant.toString());

        if(!fav_checked){
            favButton.setBackgroundColor((getResources().getColor( android.R.color.holo_red_light)));
            //favButton.setBackgroundTintMode(PorterDuff.Mode.values());
            favButton.setColorFilter((getResources().getColor( android.R.color.holo_red_light)));
            favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor( android.R.color.holo_red_light)));

            fav_checked=true;
            Toast.makeText(getBaseContext(),"Restaurant added to your favorites",Toast.LENGTH_SHORT).show();
            Map<String,Map<String,Object>> restaurantInfo = new HashMap<>();
            Map<String,Object> newRest=new HashMap<>();

            newRest.put("id",restaurant.getId());
            newRest.put("Name",restaurant.getName());
            newRest.put("location",restaurant.getLocation());
            newRest.put("Cuisines",restaurant.getCuisines());
            newRest.put("Timings",restaurant.getTimings());
            newRest.put("avCostTwo",restaurant.getAvgCostTwo());
            newRest.put("priceRange",restaurant.getPriceRange());
            newRest.put("userRating",restaurant.getUserRating());
            newRest.put("phoneNumbers",restaurant.getPhoneNumbers());
            newRest.put("establishment",restaurant.getEstablishment());
            newRest.put("featuredImage",restaurant.getFeaturedImage());
            newRest.put("all_reviews_count",restaurant.getAll_reviews_count());
            newRest.put("ReviewList",reviews);

            restaurantInfo.put(restaurant.getName(),newRest);
            databaseRepo.saveRestaurant(restaurantInfo,user);
        }
        else{//remove
            favButton.setBackgroundColor((getResources().getColor( android.R.color.white)));
            favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor( android.R.color.white)));
            favButton.setColorFilter((getResources().getColor( android.R.color.black)));
           // favButton.getI
            Toast.makeText(getBaseContext(),"Restaurant removed from your favorites",Toast.LENGTH_SHORT).show();
            fav_checked=false;
            databaseRepo.Remove(user,restaurant);

        }
    }
    public void onClickAddress(View v) {
        String[] addressSplit = address.trim().split(",");
        String newAddress = "";
        for(int word = 0; word < addressSplit.length; word++) {
            newAddress += addressSplit[word] + "+";
        }

        String query = "geo:" + latitude + "," + longitude + "?q=" + newAddress;
        Uri gmmIntentUri = Uri.parse(query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void onClickDirections(View v) {
        String query = "google.navigation:q=" + latitude + "," + longitude;
        Uri gmmIntentUri = Uri.parse(query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void generatePage(Restaurant_ restaurant, List<Review> reviewsList){
        Picasso.with(this).load(restaurant.getFeaturedImage()).into(thumb);
        //Check if restaurant is in FavList
        final  Restaurant_ tmpRestaurant= restaurant;
        fav_checked=false;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("User is "+user.getUid());
        databaseRepo.updatFavRestaurants(user).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                favRestaurantsList = databaseRepo.getFavRestaurants();
                System.out.println("Task completed!!!");

                if(databaseRepo.checkContainsFav(tmpRestaurant)){
                    System.out.println(tmpRestaurant.toString());
                    System.out.println("Enter here!!");
                    favButton.setBackgroundColor((getResources().getColor( android.R.color.holo_red_light)));
                    favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor( android.R.color.holo_red_light)));
                    favButton.setColorFilter((getResources().getColor( android.R.color.holo_red_light)));
                    fav_checked=true;
                }
                else{
                    System.out.println(tmpRestaurant.toString());
                    System.out.println("Enter not contains!!!!");
                    favButton.setBackgroundColor((getResources().getColor( android.R.color.white)));
                    favButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor( android.R.color.white)));
                    favButton.setColorFilter((getResources().getColor( android.R.color.black)));
                    fav_checked=false;
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


        restaurantName.setText(restaurant.getName());

        rating.setText(restaurant.getUserRating().getAggregateRating());
        String ratingColor = "#" + restaurant.getUserRating().getRatingColor();
        rating.setTextColor(Color.parseColor(ratingColor));

        String reviewsCountString = String.format(Locale.getDefault(), "%d reviews", restaurant.getAll_reviews_count());
        reviewsCount.setText(reviewsCountString);

        String price_category = processPriceCategory(restaurant.getPriceRange(), restaurant.getCuisines());
        priceCategory.setText(price_category);

        location.setText(restaurant.getLocation().getAddress());
        address = restaurant.getLocation().getAddress();
        latitude = restaurant.getLocation().getLatitude();
        longitude = restaurant.getLocation().getLongitude();
        //schedule.setText(restaurant.getTimings());

        String phone_numbers;
        try {
            String[] phoneNumbersMult = restaurant.getPhoneNumbers().split(",");
            phone_numbers = phoneNumbersMult[0];
        } catch(Exception e) {
            phone_numbers = restaurant.getPhoneNumbers();
        }

        phoneNumbers.setText(phone_numbers);

        recyclerView = findViewById(R.id.reviewsList);
        adapter = new ReviewsAdapter(this, reviewsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private String processPriceCategory(int price_range, String cuisines) {
        String price_category = "";
        switch(price_range) {
            case 2:
                price_category = "[€]";
                break;
            case 3:
                price_category = "[€€]";
                break;
            case 4:
                price_category = "[€€€]";
                break;
        }
        price_category += " " + cuisines;

        return price_category;
    }

}