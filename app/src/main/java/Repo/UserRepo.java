package Repo;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import FirebaseRef.FirebaseAuthRef;
import FirebaseRef.FirebaseDatabaseRef;

import com.example.snapchat.Screens.Home;
import com.example.snapchat.Screens.Snap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import Store.UserStore;

public class UserRepo {

    private static UserRepo userRepo =null;

    UserStore userStore;

    public FirebaseUser getUser() {
        return FirebaseAuthRef.getmAuth().getCurrentUser();
    }

    String userId = "";
    public void signUp(String email, String pass, final Context context) {
        FirebaseAuthRef.getmAuth().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //add to database
                userId = task.getResult().getUser().getUid();

                userStore = UserStore.getInstance();
                FirebaseDatabaseRef.getUserRef().child(userId).setValue(userStore.getUser());
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "ABC", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "CDF", Toast.LENGTH_LONG).show();
            }
        });
    }

//        //Read data
//        FirebaseDatabaseRef.getUserRef().child().addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            AccountUser user;
//                user =dataSnapshot.getValue(AccountUser.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//    }

    public void signIn (String email, String pass, final Context context) {
        FirebaseAuthRef.getmAuth().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent = new Intent(context, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public static UserRepo getInstance()
    {
        // To ensure only one instance is created
        if (userRepo == null)
        {
            userRepo = new UserRepo();
        }
        return userRepo;
    }
}