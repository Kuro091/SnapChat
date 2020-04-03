package com.example.snapchat.Screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.snapchat.FirebaseRef.FirebaseAuthRef;
import com.example.snapchat.FirebaseRef.FirebaseDatabaseRef;
import com.example.snapchat.R;
import com.example.snapchat.Store.UserStore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.example.snapchat.Entities.AccountUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFriendFragment extends Fragment {
    private View friendFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserID = mAuth.getCurrentUser().getUid();
    private DatabaseReference ListFriendRef, FriendRef;
    private List<String> listEmail = new ArrayList<String>();
    //private Bitmap finalImg;

    //private DataSnapshot dataSnapshot;


    public FindFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        friendFragmentView = inflater.inflate(R.layout.fragment_find_friend, container, false);
        ListFriendRef = FirebaseDatabase.getInstance().getReference().child("users");


        IntializeFields();

        FriendRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuthRef.getmAuth().getCurrentUser().getUid()).child("friend");
        FriendRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String email = snapshot.getKey() + "@gmail.com";
                        listEmail.add(email);
                    }
                }
                RetrieveAndDisplayGroups();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //byte[] byteArray =  getArguments().getByteArray("image");

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String currentFriendName = adapterView.getItemAtPosition(position).toString();

                Intent groupChatIntent = new Intent(getContext(), GroupChatActivity.class);
                groupChatIntent.putExtra("friendName", currentFriendName);
                //groupChatIntent.putExtra("image", byteArray);
                startActivity(groupChatIntent);
            }
        });

        return friendFragmentView;
    }


    private void IntializeFields() {
        list_view = (ListView) friendFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);

    }

    private void RetrieveAndDisplayGroups() {
        ListFriendRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

//                while (iterator.hasNext()) {
//                    set.add(((DataSnapshot) iterator.next()).getValue(AccountUser.class).getEmail());
//
//                }
//
//                String key = FirebaseAuthRef.getmAuth().getCurrentUser().getEmail();
//
//                set.remove(key);

                for (String temp: listEmail) {
                    set.add(temp);
                }


                list_of_groups.clear();
                list_of_groups.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
