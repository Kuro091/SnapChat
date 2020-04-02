package com.example.snapchat.Store;

import android.accounts.Account;

import androidx.annotation.NonNull;

import com.example.snapchat.Entities.AccountUser;
import com.example.snapchat.FirebaseRef.FirebaseAuthRef;
import com.example.snapchat.FirebaseRef.FirebaseDatabaseRef;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserStore {
    private static UserStore instance;
    AccountUser user;
    static List<AccountUser> users;

    public UserStore() {
        user = new AccountUser();
    }

    //static block initialization for exception handling
    static {
        try {
            instance = new UserStore();
            users = new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static UserStore getInstance() {
        return instance;
    }
    public static List<AccountUser> getAllUsers() {
        return users;
    }


    public AccountUser getUser() {
        return user;
    }

    public static FirebaseUser getSignedInUser() {
        if (FirebaseAuthRef.getmAuth().getCurrentUser() == null) {
            return null;
        } else {
            return FirebaseAuthRef.getmAuth().getCurrentUser();
        }
    }

    public AccountUser getSignedInUserFromDatabase() {
        return null;
    }
    public static AccountUser getUserByEmail(String email) {
        for (AccountUser user : UserStore.getAllUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
