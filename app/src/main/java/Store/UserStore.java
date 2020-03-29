package Store;

import android.accounts.Account;

import com.example.snapchat.Screens.Entities.AccountUser;

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
}
