package lupoxan.autoroom.com.autoroom11;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author lupo.xan
 * @since 13/11/2019
 * @version 0.1.0
 */
public class MyFirebase {
    private static FirebaseAuth mAuth;
    private static DatabaseReference db;

    public static boolean isReg;

    public MyFirebase(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
    }

    public boolean register(String email, String pwd){
        MyFirebase.isReg = false;
        MyFirebase.mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    MyFirebase.isReg = true;
                }
            }
        });
        return MyFirebase.isReg;
    }

    public void signOut(){
        MyFirebase.mAuth.signOut();
    }

    public FirebaseAuth getAuth(){
        return MyFirebase.mAuth;
    }

    public DatabaseReference getDb(){
        return MyFirebase.db;
    }
}
