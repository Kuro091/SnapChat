package Store;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorageRef {

    public FirebaseStorageRef (){}

    public static StorageReference getImageRef(){
        return FirebaseStorage.getInstance().getReference().child("images");
    }

}
