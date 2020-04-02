package com.example.snapchat.Screens.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snapchat.Store.UserStore;
import com.example.snapchat.R;
import com.example.snapchat.Screens.MainActivity;
import com.example.snapchat.Repo.UserRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class SignUp_Phone extends AppCompatActivity {

    UserStore userStore;
    Button btnGetCode,btnNext;
    EditText txtPhone,txtCode;
    FirebaseAuth mAuth;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__phone);

        btnGetCode = findViewById(R.id.button8);
        btnNext = findViewById(R.id.button7);
        txtPhone = findViewById(R.id.editText6);
        txtCode = findViewById(R.id.editText7);
        userStore = UserStore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifySignInCode();
//                userStore.getUser().setUsername(txtPhone.getText().toString());
//                Intent intent = new Intent(SignUp_Phone.this, SignUp_Email.class);
//                startActivity(intent);
            }
        });

    }

    private void verifySignInCode(){
        String code = txtCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            String email = userStore.getUser().getEmail();
                            String password = userStore.getUser().getPassword();
                            UserRepo.getInstance().signUp(email,password,getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(){
//        FirebaseAuthRef.getmAuth().signInWithCredential()
        String phone = txtPhone.getText().toString();

        if(phone.isEmpty()){
            txtPhone.setError("Phone number is required !!!");
            txtPhone.requestFocus();
            return;
        }

        if(phone.length() < 10){
            txtPhone.setError("Please enter a valid phone !!!");
            txtPhone.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };
}
