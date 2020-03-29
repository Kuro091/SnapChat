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

public class SignUp_Name extends AppCompatActivity {

    UserStore userStore;
    Button btnNext;
    EditText first,last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__name);
        userStore = UserStore.getInstance();
        btnNext = findViewById(R.id.button);
        first = findViewById(R.id.editText);
        last = findViewById(R.id.editText2);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = first.getText().toString().trim() + " " + last.getText().toString().trim();
                userStore.getUser().setName(fullname);
                Intent intent = new Intent(SignUp_Name.this,SignUp_Birthday.class);
                startActivity(intent);
            }
        });
    }
}
