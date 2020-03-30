package com.example.snapchat.Screens.Repo;

import android.accounts.Account;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.snapchat.Screens.Entities.AccountUser;
import com.example.snapchat.Screens.FirebaseRef.FirebaseAuthRef;
import com.example.snapchat.Screens.FirebaseRef.FirebaseDatabaseRef;
import com.example.snapchat.Screens.FirebaseRef.FirebaseStorageRef;
import com.example.snapchat.Screens.SignUp.SignUp_Email;
import com.example.snapchat.Screens.Utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;

import Store.UserStore;

public class UserRepo {
    UserStore userStore;

    public FirebaseUser getUser() {
        return FirebaseAuthRef.getmAuth().getCurrentUser();
    }

    String userId = "";
    public void signUp(String email, String pass, final Context context) {
        FirebaseAuthRef.getmAuth().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //add to database
                userId = task.getResult().getUser().getUid();

                userStore = UserStore.getInstance();
                FirebaseDatabaseRef.getUserRef().child(userId).setValue(userStore.getUser());
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "ABC", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "CDF", Toast.LENGTH_LONG).show();
            }
        });


//        //Read data
//        FirebaseDatabaseRef.getUserRef().child().addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            AccountUser user;
//                user =dataSnapshot.getValue(AccountUser.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//    }

//    signIn(){
//
//    }
    }
}
