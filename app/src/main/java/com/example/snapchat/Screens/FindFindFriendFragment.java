package com.example.snapchat.Screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.snapchat.Entities.AccountUser;
import com.example.snapchat.FirebaseRef.FirebaseAuthRef;
import com.example.snapchat.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFindFriendFragment extends Fragment {


    private View findfriendFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserID = mAuth.getCurrentUser().getUid();
    private DatabaseReference ListFriendRef,FriendRef;
    private List<String> listEmail = new ArrayList<String>();
    public FindFindFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        findfriendFragmentView = inflater.inflate(R.layout.fragment_find_find_friend, container, false);


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

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String currentFriendName = adapterView.getItemAtPosition(position).toString();

                Intent ProfileIntent = new Intent(getContext(), ProfileActivity.class);
                ProfileIntent.putExtra("friendName" , currentFriendName);
                startActivity(ProfileIntent);
            }
        });

        return findfriendFragmentView;


    }

    private void IntializeFields()
    {
        list_view = (ListView) findfriendFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);
    }

    private void RetrieveAndDisplayGroups()
    {
        ListFriendRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext())
                {
                    set.add(((DataSnapshot) iterator.next()).getValue(AccountUser.class).getEmail());

                }

                String key = FirebaseAuthRef.getmAuth().getCurrentUser().getEmail();
                set.remove(key);

                for (String temp: listEmail) {
                    set.remove(temp);
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
