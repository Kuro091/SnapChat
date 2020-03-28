package com.example.snapchat.Screens.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.snapchat.R;

public class SignUp_Password extends AppCompatActivity {

    Button btnShowHide,btnNext;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__password);

        btnShowHide = (Button) findViewById(R.id.button5);
        btnNext = (Button) findViewById(R.id.button4);
        txtPassword = (EditText) findViewById(R.id.editText4);

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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_Password.this, SignUp_Email.class);
                startActivityForResult(intent, 103);
            }
        });
    }
}
