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
import android.widget.Toast;

import Store.UserStore;
import com.example.snapchat.R;

public class SignUp_Password extends AppCompatActivity {

    Button btnShowHide,btnNext;
    EditText txtPassword;
    UserStore userStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__password);

        btnShowHide = (Button) findViewById(R.id.button5);
        btnNext = (Button) findViewById(R.id.button4);
        txtPassword = (EditText) findViewById(R.id.editText4);
        userStore = UserStore.getInstance();

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
                if(txtPassword.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Please input password",Toast.LENGTH_LONG).show();
                }
                else if(txtPassword.getText().toString().trim().length()<8){
                    Toast.makeText(getApplicationContext(),"Password must have at least 8 characters",Toast.LENGTH_LONG).show();
                }
                else {
                    userStore.getUser().setPassword(txtPassword.getText().toString());
                    Intent intent = new Intent(SignUp_Password.this, SignUp_Email.class);
                    startActivity(intent);
                }
            }
        });
    }
}
