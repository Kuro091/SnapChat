package com.example.snapchat.Screens.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.snapchat.R;

public class SignUp_Phone extends AppCompatActivity {

    TextView txtChange;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__phone);

        txtChange = findViewById(R.id.textView11);
        btnNext = findViewById(R.id.button7);

        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_Phone.this, SignUp_Email.class);
                startActivityForResult(intent,105);
            }
        });
    }
}
