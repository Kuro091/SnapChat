package com.example.snapchat.Screens.FirebaseRef;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthRef {

    public static FirebaseAuth getmAuth() {
        return FirebaseAuth.getInstance();
    }
}
