package com.example.eatanywhere.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.eatanywhere.R;
import com.example.eatanywhere.activities.MainActivity;
import com.example.eatanywhere.model.reviews.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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

    private Button saveButton;
    private EditText name,lastName,address,nationality,email;
    private EditText bornDate,phoneNumber;
    public FirebaseDatabase database;
    public FirebaseUser user;
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

        name=view.findViewById((R.id.ProfileName));
        lastName=view.findViewById((R.id.LastName));
        phoneNumber=view.findViewById((R.id.PhoneNumber));
        nationality=view.findViewById((R.id.Nationality));
        email=view.findViewById((R.id.email_profile));

        address=view.findViewById((R.id.address));
        bornDate=view.findViewById((R.id.bornDate));

        saveButton=view.findViewById((R.id.SaveChanges  ));
        database = FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        //email.setText(user.getEmail());
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag;

              //  System.out.println("ENTREI NO CLICK CARALHOOOOOOOOOOO");
                //saveProfileInfo(user,profileInfoJson());
                if(TextUtils.isEmpty(name.getText().toString().trim())){
                    name.setError("Name is required");
                    flag=false;

                }
                else flag=true;
                if(TextUtils.isEmpty(lastName.getText().toString().trim())){
                    lastName.setError("Last Name is required");
                    flag=false;

                }
                else flag=true;
                if(TextUtils.isEmpty(phoneNumber.getText().toString())){
                    phoneNumber.setError("Phone Number is required");
                    flag=false;

                }
                else flag=true;

                if(TextUtils.isEmpty(nationality.getText().toString().trim())){
                    nationality.setError("Nationality is required");
                    flag=false;

                }
               else  flag=true;

                if(TextUtils.isEmpty(address.getText().toString().trim())){
                    address.setError("Address is required");
                    flag=false;

                }
                else flag=true;

                if(TextUtils.isEmpty(bornDate.getText().toString().trim())){
                    bornDate.setError("Date is required");
                    flag=false;
                }
                else flag=true;

                if (flag){
                    JSONObject json = new JSONObject();
                    try {
                        json.put("Name",name.getText().toString().trim());
                        json.put("Last Name",lastName.getText().toString().trim());
                        json.put("Nationality",nationality.getText().toString().trim());
                        json.put("Phone Number",phoneNumber.getText().toString().trim());
                        json.put("Address",address.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("js="+json);

                    saveProfileInfo(json);
            }return;

            }
        });
    }
/*SS
    JSONObject profileInfoJson(){

        if(name.getText().toString().trim().isEmpty()){
            email.setError("Name is required");
        }
        if(lastName.getText().toString().trim().isEmpty()){
            email.setError("Last Name is required");
        }
        if(phoneNumber.getText().toString().trim().isEmpty()){
            email.setError("Phone Number is required");
        }
        if(nationality.getText().toString().trim().isEmpty()){
            email.setError("Nationality is required");
        }
        if(address.getText().toString().trim().isEmpty()){
            email.setError("Address is required");
        }
        JSONObject json = new JSONObject();
        try {
            json.put("Name",name.getText().toString().trim();
            json.put("Last Name",lastName.getText().toString().trim());
            json.put("Nationality",nationality.getText().toString().trim());
            json.put("Phone Number",phoneNumber.getText().toString().trim());
            json.put("Address",address.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
*/
   DatabaseReference saveProfileInfo( JSONObject json){
       System.out.println(user.getEmail());
       DatabaseReference i2=database.getReference().child("ProfileInfos");
       i2.setValue("OLAAAAA");
       DatabaseReference id=database.getReference().child("users/").child(user.getUid()).child("PersonalInfo");
       System.out.println("KEY="+id.getKey());

       id.setValue(json.toString());
       return id;
    }
}