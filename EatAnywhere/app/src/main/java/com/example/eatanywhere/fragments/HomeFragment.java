package com.example.eatanywhere.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eatanywhere.R;
import com.example.eatanywhere.adapter.CustomAdapter;
import com.example.eatanywhere.model.locations.ApiResponseLocation;
import com.example.eatanywhere.model.locations.LocationSuggestion;
import com.example.eatanywhere.model.restaurants.ApiResponse;
import com.example.eatanywhere.model.restaurants.Restaurant;
import com.example.eatanywhere.model.restaurants.Restaurant_;
import com.example.eatanywhere.network.RetrofitClientInstance;
import com.example.eatanywhere.network.ZomatoApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CustomAdapter adapter;
    private RecyclerView recyclerView;

    ProgressDialog progressDialog;
    private String apiKey = "37b8d502880d1f6d3bb55fc522c92f45";
    private ArrayList<Restaurant_> restaurantsList;
    private int entityId;
    private String entityType;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        final ZomatoApi service = RetrofitClientInstance.getRetrofitInstance().create(ZomatoApi.class);
        restaurantsList = new ArrayList<>();

        service.getLocation("Porto", apiKey)
                .enqueue(new Callback<ApiResponseLocation>() {
                    @Override
                    public void onResponse(Call<ApiResponseLocation> call, Response<ApiResponseLocation> response) {
                        List<LocationSuggestion> locationSuggestion = response.body().getLocationSuggestions();
                        Log.d("DEBUG", "/locations?query=Porto");
                        Log.d("DEBUG", response.body().getStatus());

                        for(int i = 0; i < locationSuggestion.size(); i++) {
                            Log.d("DEBUG", locationSuggestion.get(i).toString());
                            entityId = locationSuggestion.get(i).getEntityId();
                            Log.d("DEBUG", ""+entityId);
                            entityType = locationSuggestion.get(i).getEntityType();
                            Log.d("DEBUG", entityType);
                        }

                        service.getNearbyRestaurants(entityId, entityType, 100, 10000, apiKey)
                                .enqueue(new Callback<ApiResponse>() {
                                    @Override
                                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                        progressDialog.dismiss();
                                        Log.d("DEBUG", response.raw().toString());

                                        List<Restaurant> restaurants = response.body().getRestaurants();
                                        Log.d("DEBUG", "Restaurants list size = " + restaurants.size());


                                        /*mAdapter = new RestaurantAdapter(context, restaurantsList);
                                        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                                        mRecyclerView.addItemDecoration(itemDecoration);*/
                                        for (int i = 0; i < restaurants.size(); i++) {
                                            Log.d("DEBUG", restaurants.get(i).getRestaurant().toString());
                                            Restaurant_ rest = restaurants.get(i).getRestaurant();
                                            //Log.d("DEBUG", "Item: [" + restaurants.get(i).getRestaurant().getId() + ", " + restaurants.get(i).getRestaurant().getName() + ", " + restaurants.get(i).getRestaurant().getLocation().getLocalityVerbose() + "]");
                                            restaurantsList.add(new Restaurant_(rest.getId(),rest.getName(),rest.getLocation(), rest.getCuisines(), rest.getTimings(), rest.getAvgCostTwo(),
                                                                                rest.getPriceRange(), rest.getFeaturedImage(), rest.getUserRating(), rest.getPhoneNumbers(),
                                                                                rest.getEstablishment()));
                                            //mAdapter.notifyItemInserted(i);
                                        }
                                        generateDataList(restaurantsList);
                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage("Couldn´t find any nearby restaurants");
                                        AlertDialog mDialog = builder.create();
                                        mDialog.show();*/
                                        Log.d("DEBUG", "FAIL");
                                        Log.d("DEBUG", t.getMessage());
                                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<ApiResponseLocation> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("DEBUG", "FAIL");
                        Log.d("DEBUG", t.getMessage());
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Restaurant_> restaurantList) {
        recyclerView = getActivity().findViewById(R.id.homeRecyclerView);
        adapter = new CustomAdapter(getActivity(),restaurantList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}