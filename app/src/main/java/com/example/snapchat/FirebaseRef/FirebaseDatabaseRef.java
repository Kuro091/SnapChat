package com.example.snapchat.FirebaseRef;

import androidx.annotation.NonNull;

import com.example.snapchat.Entities.AccountUser;
import com.example.snapchat.Store.UserStore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseDatabaseRef {


    public FirebaseDatabaseRef() {

    }

    public static DatabaseReference getmDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getUserRef(){
        return FirebaseDatabaseRef.getmDatabase().child("users");
    }

    public static String getUserId(){
        return FirebaseAuthRef.getmAuth().getCurrentUser().getUid().toString();
    }

    public static DatabaseReference getMessageRef(){
        return FirebaseDatabaseRef.getmDatabase().child("users").child(getUserId()).child("messages");
    }


}
