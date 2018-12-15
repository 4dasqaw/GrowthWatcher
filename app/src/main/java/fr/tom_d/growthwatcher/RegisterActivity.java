package fr.tom_d.growthwatcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import fr.tom_d.growthwatcher.api.UserHelper;

public class RegisterActivity extends BaseActivity {

    private final static String TAG = "RegisterActivity";
    private final static int RC_SIGN_IN = 100;


    private FirebaseAuth mAuth;
    private Button mStartButton;
    private TextView mAppText;
    private EditText mPseudo, mMail, mPassword;
    private boolean isPseudoOk, isPasswordOk, isMailOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);

        isMailOk = false;
        isPasswordOk = false;
        isPseudoOk = false;


        mAppText = findViewById(R.id.register_app_text);
        mPseudo = findViewById(R.id.register_et_pseudo);
        mPassword = findViewById(R.id.register_et_password);
        mMail = findViewById(R.id.register_et_email);
        mStartButton = findViewById(R.id.register_start_button);

        mPseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() >=3){
                    isPseudoOk=true;
                }
                if (isPseudoOk && isPasswordOk && isMailOk){
                    mStartButton.setEnabled(true);
                }
                else mStartButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 5){
                    isPasswordOk = true;
                }
                else isPasswordOk = false;

                if (isPseudoOk && isPasswordOk && isMailOk){
                    mStartButton.setEnabled(true);
                }
                else mStartButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 5){
                    isMailOk = true;
                }
                else isMailOk = false;

                if (isPseudoOk && isPasswordOk && isMailOk){
                    mStartButton.setEnabled(true);
                }
                else mStartButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(mMail.getText().toString(), mPassword.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    String username = mPseudo.getText().toString();
                                    Log.d(TAG, "username : " + username);
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username).build();
                                    getCurrentUser().updateProfile(profileUpdates);
                                    createUserInFirestore();
                                    Intent mainActivity = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(mainActivity);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
        });
    }

    protected void onStart () {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent mainActivity = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(mainActivity);
        }
        else {
            // No user is signed in
        }
    }
}
