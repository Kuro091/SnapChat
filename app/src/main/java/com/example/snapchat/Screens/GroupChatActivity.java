package com.example.snapchat.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snapchat.Entities.AccountUser;
import com.example.snapchat.FirebaseRef.FirebaseAuthRef;
import com.example.snapchat.FirebaseRef.FirebaseDatabaseRef;
import com.example.snapchat.R;
import com.example.snapchat.Store.UserStore;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GroupChatActivity extends AppCompatActivity {

    private ImageButton SendMessageButton , BackButton;
    private String currentFriendName;
    private EditText userMessageInput;
    private TextView displayTextMessages;
    private ScrollView mScrollView;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, ThisAccountMessageRef, GroupMessageKeyRef, FriendAccountMessageRef,GroupMessageKeyRef2;
    private String currentUserID, currentUserName, currentDate, currentTime;

    private List<String> test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        currentFriendName = getIntent().getExtras().get("friendName").toString();
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


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");

        String currenUserEmailSubString = mAuth.getCurrentUser().getEmail().toString();
        currenUserEmailSubString =  currenUserEmailSubString.substring(0, currenUserEmailSubString.indexOf("@"));
        ThisAccountMessageRef = FirebaseDatabaseRef.getUserRef().child(FirebaseDatabaseRef.getUserId()).child("messages").child(currentFriendName);
        FriendAccountMessageRef = FirebaseDatabaseRef.getUserRef().child(clickedFriend.getId()).child("messages").child(currenUserEmailSubString);



        InitializeFields();

        GetUserInfo();


        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMessageInfoToDatabase();

                userMessageInput.setText("");

                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToChatActivity();
            }
        });


    }


    private void InitializeFields() {
        getSupportActionBar().setTitle(currentFriendName);
        BackButton = (ImageButton) findViewById(R.id.back_button);
        SendMessageButton = (ImageButton) findViewById(R.id.send_message_button);
        userMessageInput = (EditText) findViewById(R.id.input_group_message);
        displayTextMessages = (TextView) findViewById(R.id.group_chat_text_display);
        mScrollView = (ScrollView) findViewById(R.id.my_scroll_view);
    }

    private void GetUserInfo() {
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SaveMessageInfoToDatabase() {
        String message = userMessageInput.getText().toString();
        String messagekEY = ThisAccountMessageRef.push().getKey();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please write message first...", Toast.LENGTH_SHORT).show();
        } else {
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            currentTime = currentTimeFormat.format(calForTime.getTime());


            HashMap<String, Object> groupMessageKey = new HashMap<>();
            ThisAccountMessageRef.updateChildren(groupMessageKey);

            GroupMessageKeyRef = ThisAccountMessageRef.child(messagekEY);
            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);
            GroupMessageKeyRef.updateChildren(messageInfoMap);

            GroupMessageKeyRef2 = FriendAccountMessageRef.child(messagekEY);
            HashMap<String, Object> messageInfoMap2 = new HashMap<>();
            messageInfoMap2.put("name", currentUserName);
            messageInfoMap2.put("message", message);
            messageInfoMap2.put("date", currentDate);
            messageInfoMap2.put("time", currentTime);
            GroupMessageKeyRef2.updateChildren(messageInfoMap2);

        }
    }

    private void DisplayMessages(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {
//            String chatDate = (String) ((DataSnapshot)iterator.next()).getValue();
//            String chatMessage = (String) ((DataSnapshot)iterator.next()).getValue();
//            String chatName = (String) ((DataSnapshot)iterator.next()).getValue();
//            String chatTime = (String) ((DataSnapshot)iterator.next()).getValue();
//
//            displayTextMessages.append(chatName + " :\n" + chatMessage + "\n" + chatTime + "     " + chatDate + "\n\n\n");

            String chatDate = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatMessage = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatName = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatTime = (String) ((DataSnapshot) iterator.next()).getValue();

            displayTextMessages.append(chatName + " :\n" + chatMessage + "\n");

            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        ThisAccountMessageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                if (dataSnapshot.exists())
                {
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                if (dataSnapshot.exists())
                {
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToChatActivity()
    {
        Intent chatIntent = new Intent(GroupChatActivity.this, ChatActivity.class);
        startActivity(chatIntent);
    }


}
