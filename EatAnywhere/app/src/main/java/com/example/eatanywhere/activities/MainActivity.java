package com.example.eatanywhere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

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
    String searchQuery;
    final String defaultQuery = "Porto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, LOGIN_REQUEST);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();   // hide app bar

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //handleIntent(getIntent());
        /*Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "Search expanded", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "Search collapsed", Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        menu.findItem(R.id.menu_search).setOnActionExpandListener(onActionExpandListener);

        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint("Search a location");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //query = searchView.getQuery().toString();
        System.out.println("SEARCH QUERY: " + searchQuery);
        System.out.println("DEFAULT QUERY: " + defaultQuery);
        //searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));

        return true;
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
                            //selectedFragment = new HomeFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("searchQuery", searchQuery);
                            bundle.putString("defaultQuery", defaultQuery);
                            selectedFragment = new HomeFragment();
                            selectedFragment.setArguments(bundle);
                            //selectedFragment = HomeFragment.newInstance(searchQuery, defaultQuery);
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