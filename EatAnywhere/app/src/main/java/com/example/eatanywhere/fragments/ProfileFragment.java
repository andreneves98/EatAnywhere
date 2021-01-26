package com.example.eatanywhere.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatanywhere.R;
import com.example.eatanywhere.activities.LoginActivity;
import com.example.eatanywhere.activities.MainActivity;
import com.example.eatanywhere.model.reviews.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private EditText name,lastName,address,nationality,email;
    //private EditText phoneNumber;

    private ImageView profilePic;
    private TextView fullName;
    private TextView email;
    private Button saveButton;
    private Button logOutButton;

    private FirebaseUser user;
    private FirebaseFirestore db;
    public static final int LOGIN_REQUEST = 1;
    private boolean containsInfo;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        generateProfile();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile,container,false);


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profilePic = view.findViewById(R.id.profilePicture);
        profilePic.setClipToOutline(true);

        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.email);

        /*name=view.findViewById(R.id.ProfileName);
        lastName=view.findViewById(R.id.LastName);
        phoneNumber=view.findViewById(R.id.PhoneNumber);
        nationality=view.findViewById(R.id.Nationality);
        email=view.findViewById(R.id.email_profile);
        address=view.findViewById(R.id.address);*/

        logOutButton=view.findViewById(R.id.logOut);
        //saveButton=view.findViewById(R.id.SaveChanges);

        //email.setText(user.getEmail());
        //email.setEnabled(false);

        //email.setText(user.getEmail());



        //containsInfo=checkUserContainsInfo();
        /*saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag;
                System.out.println("ContainsInfo=" + containsInfo);
                if (!containsInfo) {
                    if (TextUtils.isEmpty(name.getText().toString().trim())) {
                        name.setError("Name is required");
                        flag = false;

                    } else flag = true;
                    if (TextUtils.isEmpty(lastName.getText().toString().trim())) {
                        lastName.setError("Last Name is required");
                        flag = false;

                    } else flag = true;
                    if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                        phoneNumber.setError("Phone Number is required");
                        flag = false;

                    } else flag = true;

                    if (TextUtils.isEmpty(nationality.getText().toString().trim())) {
                        nationality.setError("Nationality is required");
                        flag = false;

                    } else flag = true;

                    if (TextUtils.isEmpty(address.getText().toString().trim())) {
                        address.setError("Address is required");
                        flag = false;

                    } else flag = true;

                    if (flag) saveProfileInfo();

                    return;

                }
                else{
                    saveProfileInfo();
                }
            }
        });*/

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"User " +user.getEmail() + " logged Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void generateProfile() {
        db.collection(user.getUid()).document("profileInfo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                String fullNameString = document.getData().get("name").toString() + " " + document.getData().get("lastName").toString();
                                fullName.setText(fullNameString);

                                email.setText(document.getData().get("email").toString());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /*private boolean checkUserContainsInfo(){
        System.out.println("ID="+user.getUid());
        final boolean[] flag = new boolean[1];
       // System.out.println()
        db.collection(user.getUid()).document("profileInfo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document=task.getResult();
                            if(document.exists()){
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                email.setText(document.getData().get("email").toString());
                                name.setText(document.getData().get("name").toString());
                                lastName.setText(document.getData().get("lastName").toString());
                                address.setText(document.getData().get("address").toString());
                                nationality.setText(document.getData().get("nationality").toString());
                                phoneNumber.setText(document.getData().get("phoneNumber").toString());
                                }
                            flag[0] =true;

                        }else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                            flag[0] =false;
                        }
                    }
                });

        return flag[0];
    }*/

    /*void saveProfileInfo(){

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", name.getText().toString().trim());
        userInfo.put("lastName", lastName.getText().toString().trim());
        userInfo.put("email", user.getEmail());
        userInfo.put("address", address.getText().toString().trim());
        userInfo.put("nationality", nationality.getText().toString().trim());
        userInfo.put("phoneNumber", phoneNumber.getText().toString().trim());
        storeInfoInDatabase(userInfo);

    }*/

    void storeInfoInDatabase( Map userInfo ){

       // Add a new document with a generated ID
       db.collection(user.getUid()).document("profileInfo")
               .set(userInfo)
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

       System.out.println(userInfo.toString());
    }
}