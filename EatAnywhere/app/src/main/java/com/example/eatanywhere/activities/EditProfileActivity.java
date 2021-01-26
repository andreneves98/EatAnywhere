package com.example.eatanywhere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eatanywhere.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseFirestore db;

    private ImageView editPic;
    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText address;
    private EditText nationality;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle(R.string.edit_profile_activity_name);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        editPic = findViewById(R.id.editProfilePicture);
        editPic.setClipToOutline(true);

        email = findViewById(R.id.email_profile);
        email.setEnabled(false);

        firstName = findViewById(R.id.user_first_name);
        lastName = findViewById(R.id.LastName);
        phoneNumber = findViewById(R.id.PhoneNumber);
        address = findViewById(R.id.address);
        nationality = findViewById(R.id.Nationality);
        saveButton = findViewById(R.id.SaveChanges);


        db = FirebaseFirestore.getInstance();
        final boolean containsInfo = checkUserContainsInfo();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag;
                System.out.println("ContainsInfo=" + containsInfo);
                if (!containsInfo) {
                    if (TextUtils.isEmpty(firstName.getText().toString().trim())) {
                        firstName.setError("Name is required");
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
                else {
                    saveProfileInfo();

                    /*Intent returnIntent = new Intent();
                    returnIntent.putExtra("firstName", firstName.getText().toString().trim());
                    returnIntent.putExtra("lastName", lastName.getText().toString().trim());
                    returnIntent.putExtra("phoneNumber", phoneNumber.getText().toString().trim());
                    returnIntent.putExtra("address", address.getText().toString().trim());
                    returnIntent.putExtra("nationality", nationality.getText().toString().trim());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();*/
                }

            }
        });

    }

    private boolean checkUserContainsInfo(){
        System.out.println("ID="+user.getUid());
        final boolean[] flag = new boolean[1];

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
                                firstName.setText(document.getData().get("name").toString());
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
    }

    void saveProfileInfo(){

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", firstName.getText().toString().trim());
        userInfo.put("lastName", lastName.getText().toString().trim());
        userInfo.put("email", user.getEmail());
        userInfo.put("address", address.getText().toString().trim());
        userInfo.put("nationality", nationality.getText().toString().trim());
        userInfo.put("phoneNumber", phoneNumber.getText().toString().trim());
        storeInfoInDatabase(userInfo);

        Toast.makeText(EditProfileActivity.this, "Your changes have been saved!", Toast.LENGTH_SHORT).show();
    }

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