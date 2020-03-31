package com.example.snapchat.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.snapchat.R;
import com.example.snapchat.Screens.Repo.UserRepo;
import com.example.snapchat.Screens.SignUp.SignUp_Email;
import com.example.snapchat.Screens.SignUp.SignUp_Password;

public class SignIn extends AppCompatActivity {
    Button btnShowHide,btnSignIn;
    EditText txtEmail,txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btnShowHide = (Button) findViewById(R.id.button12);
        btnSignIn = (Button) findViewById(R.id.button11);
        txtEmail = (EditText) findViewById(R.id.editText9);
        txtPassword = (EditText) findViewById(R.id.editText10);

        btnShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnShowHide.getText().toString().equals("Show")){
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnShowHide.setText("Hide");
                } else{
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnShowHide.setText("Show");
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserRepo().signIn(txtPassword.getText().toString(),txtPassword.getText().toString(),getApplicationContext());
            }
        });
    }
}
