package com.example.snapchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import Store.UserStore;

public class Home extends AppCompatActivity {
    TextView txtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtUserName = findViewById(R.id.textView11);

        txtUserName.setText(UserStore.getSignedInUser().getDisplayName());
    }
}
