package Store;

import android.accounts.Account;

import com.example.snapchat.Screens.Entities.AccountUser;
import com.example.snapchat.Screens.FirebaseRef.FirebaseAuthRef;
import com.google.firebase.auth.FirebaseUser;

public class UserStore {
    private static UserStore instance;
    AccountUser user;

    public UserStore(){
        user = new AccountUser();
    }

    //static block initialization for exception handling
    static{
        try{
            instance = new UserStore();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static UserStore getInstance(){
        return instance;
    }

    public AccountUser getUser() {
        return user;
    }

    public static FirebaseUser getSignedInUser(){
        return FirebaseAuthRef.getmAuth().getCurrentUser()!=null ? FirebaseAuthRef.getmAuth().getCurrentUser() : null;
    }

    public AccountUser getSignedInUserFromDatabase(){
            return null;
    }
}
