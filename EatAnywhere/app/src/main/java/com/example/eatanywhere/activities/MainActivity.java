package com.example.eatanywhere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.eatanywhere.R;
import com.example.eatanywhere.fragments.FavoritesFragment;
import com.example.eatanywhere.fragments.HomeFragment;
import com.example.eatanywhere.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    FirebaseUser user;
    public static final int LOGIN_REQUEST = 1;
    private String[] sortOptions = {"Most popular", "Cost", "Rating"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, LOGIN_REQUEST);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();   // hide app bar

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        // isto aqui dá erro porque o user ta a null, não usar
        //Toast.makeText(this, "You are loggedIn as\t" + user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                user = data.getParcelableExtra("User");
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String title = null;

                    switch(item.getItemId()) {
                        case R.id.homeFragment:
                            selectedFragment = new HomeFragment();
                            title = "Near your location";
                            break;
                        case R.id.favoritesFragment:
                            selectedFragment = new FavoritesFragment();
                            title = "Favorite restaurants";
                            break;
                        case R.id.profileFragment:
                            selectedFragment = new ProfileFragment();
                            title = "Profile";
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
                    setTitle(title); // change appbar title

                    return true;
                }
            };


    public FirebaseUser getUser(){
        return user;
    }
}