package com.example.snapchat.Screens.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Store.UserStore;
import com.example.snapchat.R;

public class SignUp_Username extends AppCompatActivity {

    Button btnNext;
    UserStore userStore;
    EditText txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__username);
        btnNext = findViewById(R.id.button3);
        userStore = UserStore.getInstance();
        txtUsername = findViewById(R.id.editText3);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            userStore.getUser().setUsername(txtUsername.getText().toString());
            Intent intent = new Intent(SignUp_Username.this,SignUp_Password.class);
            startActivity(intent);
            }
        });
    }
}
