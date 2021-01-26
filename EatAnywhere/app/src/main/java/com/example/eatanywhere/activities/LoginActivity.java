package com.example.eatanywhere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eatanywhere.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button logInButton;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInButton = findViewById(R.id.LogIn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        this.getSupportActionBar().hide();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = email.getText().toString().trim();
                String mpassword = password.getText().toString().trim();
                System.out.println("FIREBASEAUTH==="+firebaseAuth.getApp().toString());

                if(TextUtils.isEmpty(memail)) {
                    email.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(mpassword)) {
                    password.setError("Password is required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(memail, mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            user = task.getResult().getUser();

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("User", user);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();                                       // close this activity and resume main

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
   /* void signOut() async{
        try {
            user= (await FirebaseAuth.instance.currentUser);
            await FirebaseAuth.instance.signOut();
            return user.uid;
        } catch(e) {
            print(e.toString());
            isLogged=false;
            return null;
        }*/
}
