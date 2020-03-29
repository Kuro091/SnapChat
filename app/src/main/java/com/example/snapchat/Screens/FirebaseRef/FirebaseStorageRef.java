package com.example.snapchat.Screens.FirebaseRef;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorageRef {

    public FirebaseStorageRef (){}

    public static StorageReference getImageRef(){
        return FirebaseStorage.getInstance().getReference().child("images");
    }

    public static StorageReference getUserRefByUserId(String userId){
        return FirebaseStorage.getInstance().getReference().child("users/" + userId);
    }
}
