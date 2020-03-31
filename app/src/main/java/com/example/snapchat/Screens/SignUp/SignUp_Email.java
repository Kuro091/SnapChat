package com.example.snapchat.Screens.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Store.UserStore;
import com.example.snapchat.R;

import Repo.UserRepo;

public class SignUp_Email extends AppCompatActivity {

    Button btnNext;
    UserStore userStore;
    EditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__email);

        userStore = UserStore.getInstance();
        btnNext = findViewById(R.id.button6);
        txtEmail = findViewById(R.id.editText5);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userStore.getUser().setEmail(txtEmail.getText().toString());

                String email = userStore.getUser().getEmail();
                String password = userStore.getUser().getPassword();
                UserRepo.getInstance().signUp(email,password,getApplicationContext());

                UserRepo.getInstance().signIn(email, password, getApplicationContext());
                //Intent intent = new Intent(getApplicationContext(), SignUp_Phone.class);
                //startActivity(intent);
            }
        });
    }
}
