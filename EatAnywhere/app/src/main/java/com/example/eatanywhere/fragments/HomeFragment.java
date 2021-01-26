package com.example.eatanywhere.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatanywhere.R;
import com.example.eatanywhere.RecyclerViewClickInterface;
import com.example.eatanywhere.activities.MainActivity;
import com.example.eatanywhere.activities.MapsActivity;
import com.example.eatanywhere.activities.RestaurantDetailsActivity;
import com.example.eatanywhere.adapter.HomeAdapter;
import com.example.eatanywhere.model.locations.ApiResponseLocation;
import com.example.eatanywhere.model.locations.LocationSuggestion;
import com.example.eatanywhere.model.restaurants.ApiResponse;
import com.example.eatanywhere.model.restaurants.Restaurant;
import com.example.eatanywhere.model.restaurants.Restaurant_;
import com.example.eatanywhere.model.reviews.Review;
import com.example.eatanywhere.model.reviews.Reviews;
import com.example.eatanywhere.model.reviews.UserReview;
import com.example.eatanywhere.network.RetrofitClientInstance;
import com.example.eatanywhere.network.ZomatoApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerViewClickInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    private Button sortButton,mapButton;
    private String[] sortOptions = {"Most Popular", "Cost", "Rating"};

    ProgressDialog progressDialog;
    private String apiKey = "37b8d502880d1f6d3bb55fc522c92f45";
    private ArrayList<Restaurant_> restaurantsList;
    private int entityId;
    private String entityType;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ZomatoApi service;

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

        getActivity().setTitle(R.string.app_name);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mapButton = view.findViewById(R.id.map_button);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        service = RetrofitClientInstance.getRetrofitInstance().create(ZomatoApi.class);
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
                                                    rest.getEstablishment(), rest.getAll_reviews_count()));
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



        final Button sortButton = view.findViewById(R.id.sort_button);
        sortButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.sort_popout, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                //Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);

                Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.popupspinner);

                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, sortOptions);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                popupSpinner.setAdapter(adapter);



                /*view.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(popupWindow.isShowing())
                            popupWindow.dismiss();
                    }});*/

                popupWindow.showAsDropDown(sortButton, 20, 0);

            }

        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsActivity= new Intent(getActivity(),MapsActivity.class);
                startActivity(mapsActivity);
            }
        });

        return view;
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Restaurant_> restaurantList) {
        recyclerView = getActivity().findViewById(R.id.homeRecyclerView);
        adapter = new HomeAdapter(getActivity(), restaurantList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(final int position) {
        Toast.makeText(getActivity(), restaurantsList.get(position).getName(), Toast.LENGTH_SHORT).show();

        service.getRestaurantReviews(Integer.parseInt(restaurantsList.get(position).getId()), apiKey)
                .enqueue(new Callback<Reviews>() {
                    @Override
                    public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                        List<UserReview> userReviews = response.body().getUserReviews();
                        Log.d("DEBUG", ""+userReviews.size());
                        Log.d("DEBUG", response.body().toString());

                        ArrayList<Review> restaurantReviews = new ArrayList<>();
                        for(int i = 0; i < userReviews.size(); i++) {
                            restaurantReviews.add(userReviews.get(i).getReview());
                        }

                        Intent restaurantIntent = new Intent(getActivity(), RestaurantDetailsActivity.class);
                        restaurantIntent.putExtra("restaurant", restaurantsList.get(position));

                        Gson gson = new Gson();
                        String jsonReviews = gson.toJson(restaurantReviews);

                        restaurantIntent.putExtra("reviews", jsonReviews);
                        startActivity(restaurantIntent);
                    }

                    @Override
                    public void onFailure(Call<Reviews> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}