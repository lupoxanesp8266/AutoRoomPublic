package lupoxan.autoroom.com.autoroom11;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/**
 * @author lupo.xan
 * @version 0.0.1
 * @since 16/01/2020
 */
public class SplashScreen extends AppCompatActivity {


    private MyFirebase firebaseObj = new MyFirebase();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseObj.getAuth().getCurrentUser() != null) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    overridePendingTransition(R.anim.splash_in,R.anim.splash_out);
                    finish();
                }else{
                    startActivity(new Intent(SplashScreen.this, LogIn.class));
                    overridePendingTransition(R.anim.splash_in,R.anim.splash_out);
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }


}
