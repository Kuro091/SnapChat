package com.example.snapchat.Screens.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snapchat.R;

import java.util.Calendar;
import java.util.Date;

import com.example.snapchat.Store.UserStore;

public class SignUp_Birthday extends AppCompatActivity {

    private static final String TAG = "SignUp_Birthday";
    private TextView DisplayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    boolean selected = false;
    Button btnNext;
    UserStore userStore;
    int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__birthday);
        userStore = UserStore.getInstance();

        btnNext = findViewById(R.id.button2);
        DisplayDate = (TextView) findViewById(R.id.textView4);
        DisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal =Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignUp_Birthday.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month +1;
                String date = month + "/" + day + "/" + year;
                DisplayDate.setText(date);
                selected = true;
            }
        };

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selected){
                    Toast.makeText(getApplicationContext(),"Please select date",Toast.LENGTH_LONG).show();
                }
                else {
                    Date birthday = new Date(year, month, day);
                    userStore.getUser().setDob(birthday);
                    Intent intent = new Intent(SignUp_Birthday.this, SignUp_Username.class);
                    startActivity(intent);
                }
            }
        });
    }
}
