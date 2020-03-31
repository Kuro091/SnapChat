package Store;

import Entities.AccountUser;
import FirebaseRef.FirebaseAuthRef;
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
        if(FirebaseAuthRef.getmAuth().getCurrentUser()==null){
            return null;
        }else{
            return FirebaseAuthRef.getmAuth().getCurrentUser();
        }
    }

    public AccountUser getSignedInUserFromDatabase(){
            return null;
    }
}
