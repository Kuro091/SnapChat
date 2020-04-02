package com.example.snapchat.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.snapchat.Entities.AccountUser;
import com.example.snapchat.R;
import com.example.snapchat.Store.UserStore;

public class ProfileActivity extends AppCompatActivity {

    Button Backbutton , AddFriendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //currentFriendName = getIntent().getExtras().get("friendName").toString();
        InitializeFields();


        AddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendFunction();
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
        Intent chatIntent = new Intent(GroupChatActivity.this, ChatActivity.class);
        startActivity(chatIntent);
    }

    private void AddFriendFunction(){

        AccountUser clickedFriend = UserStore.getUserByEmail(currentFriendName);

    }


}
