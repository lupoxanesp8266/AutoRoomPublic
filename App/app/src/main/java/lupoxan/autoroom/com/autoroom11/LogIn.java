package lupoxan.autoroom.com.autoroom11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


/**
 * LogIn Class
 *
 * @author lupo.xan
 * @version 0.2.5
 * @since 31/03/2019
 */
public class LogIn extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText txtUser, txtPass;

    private GoogleApiClient googleApiClient;

    private ProgressDialog progressDialog;
    private MyFirebase firebaseObj = new MyFirebase();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    public static final int SIGN_IN_CODE = 1;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseObj.getAuth().getCurrentUser() != null) {
                    startActivity(new Intent(LogIn.this, MainActivity.class));
                    finish();
                }
            }
        };
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        txtUser = findViewById(R.id.user);
        txtPass = findViewById(R.id.pass);

        Button btnLogIn = findViewById(R.id.login);
        Button btnReg = findViewById(R.id.register);

        SignInButton signInButton = findViewById(R.id.signInButton);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });




        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = txtUser.getText().toString().trim();
                String pss = txtPass.getText().toString().trim();

                if (TextUtils.isEmpty(usr)) {
                    Toast.makeText(LogIn.this, R.string.error_field_required, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pss)) {
                    Toast.makeText(getApplicationContext(), R.string.error_field_required, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage(getString(R.string.log_in));
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(usr, pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = txtUser.getText().toString().trim();
                String pss = txtPass.getText().toString().trim();

                if (TextUtils.isEmpty(usr)) {
                    Toast.makeText(LogIn.this, R.string.error_field_required, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pss)) {
                    Toast.makeText(getApplicationContext(), R.string.error_field_required, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage(getString(R.string.registrando));
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(usr, pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            firebaseAuthWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(this, R.string.error_inicio_usuario, Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.error_inicio_usuario, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
