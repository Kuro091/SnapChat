package com.example.snapchat.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snapchat.Entities.AccountUser;
import com.example.snapchat.FirebaseRef.FirebaseAuthRef;
import com.example.snapchat.FirebaseRef.FirebaseDatabaseRef;
import com.example.snapchat.R;
import com.example.snapchat.Store.UserStore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    Button Backbutton , AddFriendButton;
    private String currentFriendName,currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef,ThisAccountMessageRef,GroupMessageKeyRef,FriendAccountMessageRef,GroupMessageKeyRef2;
    private List<String> test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        currentFriendName = getIntent().getExtras().get("friendName").toString();
        InitializeFields();


        AddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendFunction();
                SendUserToChatActivity();
            }
        });

        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToChatActivity();
            }
        });


    }


    private void InitializeFields() {

        Backbutton = findViewById(R.id.Back_btn);
        AddFriendButton = findViewById(R.id.add_friend_button);
    }


    private void SendUserToChatActivity()
    {
        Intent chatIntent = new Intent(ProfileActivity.this, ChatActivity.class);
        startActivity(chatIntent);
    }

    private void AddFriendFunction(){



        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");


        test = new ArrayList<>();
        while(UserStore.getAllUsers()==null){
            Toast.makeText(this, "Still fetching data", Toast.LENGTH_LONG);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        if(UserStore.getAllUsers()!=null){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        AccountUser clickedFriend = UserStore.getUserByEmail(currentFriendName);
        currentFriendName = currentFriendName.substring(0, currentFriendName.indexOf("@"));
        String currenUserEmailSubString = mAuth.getCurrentUser().getEmail().toString();
        currenUserEmailSubString =  currenUserEmailSubString.substring(0, currenUserEmailSubString.indexOf("@"));

        ThisAccountMessageRef = FirebaseDatabaseRef.getUserRef().child(FirebaseDatabaseRef.getUserId()).child("friend").child(currentFriendName);
        FriendAccountMessageRef = FirebaseDatabaseRef.getUserRef().child(clickedFriend.getId()).child("friend").child(currenUserEmailSubString);


        String friendkEY = ThisAccountMessageRef.push().getKey();

        HashMap<String, Object> groupMessageKey = new HashMap<>();
        ThisAccountMessageRef.updateChildren(groupMessageKey);

        GroupMessageKeyRef = ThisAccountMessageRef.child(friendkEY);
        HashMap<String, Object> friendInfoMap = new HashMap<>();


        friendInfoMap.put("id", clickedFriend.getId());
        friendInfoMap.put("name", clickedFriend.getName());
        friendInfoMap.put("email", clickedFriend.getEmail());
        GroupMessageKeyRef.updateChildren(friendInfoMap);

        GroupMessageKeyRef2 = FriendAccountMessageRef.child(friendkEY);
        HashMap<String, Object> friendInfoMap2 = new HashMap<>();
        friendInfoMap2.put("id", mAuth.getCurrentUser().getUid());
        friendInfoMap2.put("name", mAuth.getCurrentUser().getDisplayName());
        friendInfoMap2.put("email", mAuth.getCurrentUser().getEmail());
        GroupMessageKeyRef2.updateChildren(friendInfoMap2);


    }


}
