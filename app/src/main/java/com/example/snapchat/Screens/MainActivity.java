package com.example.snapchat.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.snapchat.R;
import com.example.snapchat.Screens.FirebaseRef.FirebaseAuthRef;
import com.example.snapchat.Screens.Repo.UserRepo;
import com.example.snapchat.Screens.SignUp.SignUp_Name;

public class MainActivity extends AppCompatActivity {
    Button btnSignUp,btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
if(FirebaseAuthRef.getmAuth().getCurrentUser()!=null){
    UserRepo.
}
        btnSignIn = findViewById(R.id.button10);
        btnSignUp = findViewById(R.id.button9);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp_Name.class);
                startActivity(intent);
            }
        });
    }
}
