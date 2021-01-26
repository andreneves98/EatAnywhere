package com.example.eatanywhere.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eatanywhere.RecyclerViewClickInterface;


import com.example.eatanywhere.R;
import com.example.eatanywhere.activities.RestaurantDetailsActivity;
import com.example.eatanywhere.activities.databaseRepo;
import com.example.eatanywhere.adapter.FavoriteAdapter;
import com.example.eatanywhere.model.restaurants.Restaurant_;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment implements RecyclerViewClickInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //private FavoritesAdapter;
    private RecyclerView recyclerView;
    private List<Restaurant_> favRestaurantsList;
    private ProgressBar progressBar;

    private FavoriteAdapter favoriteAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getActivity().setTitle(R.string.app_name);


        //progressBar= FavoritesFragment.getafindViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);


    }

    private void generateDataList(List<Restaurant_> restaurantList) {

        recyclerView =this.getActivity().findViewById(R.id.favoriteRecyclerView);
        System.out.println("RECYCLER="+recyclerView.toString());

        favoriteAdapter = new FavoriteAdapter(getActivity(), restaurantList, this);

       // recyclerView.setLayoutManager(getActivity()));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }
    @Override
    public void onResume() {
        progressBar=getActivity().findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("User is "+user.getUid());
        databaseRepo.updatFavRestaurants(user).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                System.out.println("Task completed!!!");
                favRestaurantsList = databaseRepo.getFavRestaurants();
                System.out.println("Size is ="+favRestaurantsList.size());
                generateDataList(favRestaurantsList);
                progressBar.setVisibility(View.INVISIBLE);


            }
        });
      //  System.out.println("FavRestaurantList="+favRestaurantsList);

        super.onResume();

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), favRestaurantsList.get(position).getName(), Toast.LENGTH_SHORT).show();

        Intent restaurantIntent = new Intent(getActivity(), RestaurantDetailsActivity.class);
        restaurantIntent.putExtra("restaurant", favRestaurantsList.get(position));

        startActivity(restaurantIntent);

    }


}