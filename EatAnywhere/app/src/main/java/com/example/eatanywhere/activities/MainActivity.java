package com.example.eatanywhere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.eatanywhere.R;
import com.example.eatanywhere.fragments.FavoritesFragment;
import com.example.eatanywhere.fragments.HomeFragment;
import com.example.eatanywhere.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    FirebaseUser User;

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
        User=getIntent().getParcelableExtra("User");

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Toast.makeText(this, "You are loggedIn as\t" + User.getEmail(), Toast.LENGTH_SHORT).show();


    }
    public FirebaseUser getUser(){
        return User;
    }
}