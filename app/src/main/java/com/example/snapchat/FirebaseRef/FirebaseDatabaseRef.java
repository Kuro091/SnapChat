package com.example.snapchat.FirebaseRef;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseRef {

    public static DatabaseReference getmDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getUserRef(){
        return FirebaseDatabaseRef.getmDatabase().child("users");
    }
}
