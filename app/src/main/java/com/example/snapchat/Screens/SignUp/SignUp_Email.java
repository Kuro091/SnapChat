package com.example.snapchat.Screens.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snapchat.Store.UserStore;
import com.example.snapchat.R;

import com.example.snapchat.Repo.UserRepo;

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
                if(!txtEmail.getText().toString().matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")){
                    Toast.makeText(getApplicationContext(),"Please input valid email",Toast.LENGTH_LONG).show();
                }
                else {
                    userStore.getUser().setEmail(txtEmail.getText().toString());
                    String email = userStore.getUser().getEmail();
                    String password = userStore.getUser().getPassword();
                    UserRepo.getInstance().signUp(email, password, getApplicationContext());

                    UserRepo.getInstance().signIn(email, password, getApplicationContext());
                }
            }
        });
    }
}
