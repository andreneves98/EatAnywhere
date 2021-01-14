package com.example.eatanywhere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.eatanywhere.fragments.FavoritesFragment;
import com.example.eatanywhere.fragments.HomeFragment;
import com.example.eatanywhere.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();   // hide app bar

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }
}