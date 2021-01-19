package com.example.eatanywhere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eatanywhere.R;
import com.example.eatanywhere.model.reviews.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    EditText email,password;
    Button logInButton;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logInButton=findViewById((R.id.LogIn));
        email=findViewById((R.id.email));
        password=findViewById((R.id.password));
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById((R.id.progressBar));
        this.getSupportActionBar().hide();
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail=email.getText().toString().trim();
                String mpassword=password.getText().toString().trim();

                if(TextUtils.isEmpty(memail)){
                    email.setError("Email is required");
                    return ;
                }
                if(TextUtils.isEmpty(mpassword)){
                    email.setError("Password is required");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(memail,mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            user=task.getResult().getUser();
                            //Toast.makeText(LogIn.this,"LogIn Sucessfully" + user , Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("User",user);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LogIn.this,"Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
