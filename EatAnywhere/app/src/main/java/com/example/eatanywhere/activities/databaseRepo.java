package com.example.eatanywhere.activities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eatanywhere.model.restaurants.Location;
import com.example.eatanywhere.model.restaurants.Restaurant;
import com.example.eatanywhere.model.restaurants.Restaurant_;
import com.example.eatanywhere.model.restaurants.UserRating;
import com.example.eatanywhere.model.reviews.Review;
import com.example.eatanywhere.model.reviews.Reviews;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class databaseRepo {

    private  static List<Review> reviewsList=new LinkedList<>();
    private static List <Restaurant_> restaurants;


    public static void saveRestaurant(Map<String, Map<String, Object>> restaurant, FirebaseUser user){
        // Add a new document with a generated ID
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection(user.getUid()).document("Favorites")
                .set(restaurant, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });

        System.out.println(restaurant.toString());
    }

    public static Task updatFavRestaurants(FirebaseUser user){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        restaurants=new LinkedList<>();
        System.out.println("ID="+user.getUid());
        final boolean[] flag = new boolean[1];
        // System.out.println()
        Task t= db.collection(user.getUid()).document("Favorites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document=task.getResult();
                            if(document.exists()){
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                //System.out.println("Data="+document.getData().keySet().toString());
                                Map<String,Object> restMap= document.getData();
                                Object [] mapKeys= restMap.keySet().toArray();

                                for (int i=0; i<mapKeys.length;i++){
                                    Map<String,Object> restaurantMap= (Map<String, Object>) restMap.get(mapKeys[i]);
                                    System.out.println("Restaurante["+restaurantMap.get("Name")+"]=" + restaurantMap);
                                    //Restaurant parameters
                                    String id=restaurantMap.get("id").toString();
                                    String name=restaurantMap.get("Name").toString();
                                    //Location parameters
                                    Map<String,Object> locationMap=(Map<String, Object>)restaurantMap.get("location");
                                    System.out.println("LocationMap="+locationMap.toString());
                                    String address=locationMap.get("address").toString();
                                    String city=locationMap.get("city").toString();
                                    String locality=locationMap.get("locality").toString();
                                    String zipcode=locationMap.get("zipcode").toString();
                                    String longitude=locationMap.get("longitude").toString();
                                    String latitude=locationMap.get("latitude").toString();
                                    String localityVerbose=locationMap.get("localityVerbose").toString();
                                    int cityId=Integer.parseInt(locationMap.get("cityId").toString());
                                    int countryId=Integer.parseInt(locationMap.get("countryId").toString());
                                    Location location= new Location(address,locality,city,cityId,latitude,longitude,
                                            zipcode,countryId,localityVerbose);
                                    //System.out.println("Location="+location);
                                    //User Rating parameters
                                    Map<String,Object> userRatingMap=(Map<String, Object>)restaurantMap.get("userRating");
                                    //System.out.println("userRatingMap="+userRatingMap);

                                    String aggregateRating=userRatingMap.get("aggregateRating").toString();
                                    String ratingText=userRatingMap.get("ratingText").toString();
                                    String ratingColor=userRatingMap.get("ratingColor").toString();
                                    int votes=Integer.parseInt(userRatingMap.get("votes").toString());

                                    UserRating userRating=new UserRating(aggregateRating,ratingText,ratingColor,votes);
                                    System.out.println("userRating="+userRating);
                                    List<String> establishment=( List<String> )restaurantMap.get("establishment");
                                    //System.out.println("Establishment="+establishment);
                                    String cuisines=restaurantMap.get("Cuisines").toString();
                                    String Timings=restaurantMap.get("Timings").toString();
                                    int priceRange= Integer.parseInt(restaurantMap.get("priceRange").toString());
                                    int avCostTwo= Integer.parseInt(restaurantMap.get("avCostTwo").toString());
                                    String featuredImage=restaurantMap.get("featuredImage").toString();
                                    String phoneNumbers=restaurantMap.get("phoneNumbers").toString();
                                    int all_reviews_count=Integer.parseInt(restaurantMap.get("all_reviews_count").toString());
                                    //Map<String,Object> reviewsMap= (Map<String,Object>)restaurantMap.get("ReviewList");

                                    reviewsList= (List<Review>)restaurantMap.get("ReviewList");
                                    Restaurant_ restaurant=new Restaurant_(id,name,location,cuisines,Timings,avCostTwo,priceRange,
                                            featuredImage,userRating,phoneNumbers,establishment,all_reviews_count);
                                    restaurants.add(restaurant);
                                }
                                // document.getData().;
                                //Restaurant_ tmpRest= new Restaurant_();

                            }
                            flag[0] =true;

                        }else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                            flag[0] =false;
                        }
                    }
                });

        return t;
    }
    public static List<Restaurant_> getFavRestaurants() { return restaurants; }

    public static boolean checkContainsFav(Restaurant_ restaurant){

        for (int i=0; i<restaurants.size();i++){
            if(restaurant.getName().equals(restaurants.get(i).getName()))
                return true;
        }

        return false;
    }



    public static Task Remove(FirebaseUser user,Restaurant_ restaurant){
        FirebaseFirestore db= FirebaseFirestore.getInstance();

        DocumentReference docRef=db.collection(user.getUid()).document("Favorites");
        Map<String,Object> removeRest=new HashMap<>();
        removeRest.put(restaurant.getName(), FieldValue.delete());
        Task t =docRef.update(removeRest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
        return t;
    }
    public List<Review> getReviewList(){
        return reviewsList;
    }




}
