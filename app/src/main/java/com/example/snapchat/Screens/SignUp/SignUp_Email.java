package com.example.snapchat.Screens.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Store.UserStore;
import com.example.snapchat.R;
import com.example.snapchat.Screens.MainActivity;
import com.example.snapchat.Screens.Repo.UserRepo;

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
                new UserRepo().signUp(email,password,getApplicationContext());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
