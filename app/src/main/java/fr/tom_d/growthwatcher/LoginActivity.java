package fr.tom_d.growthwatcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "LoginActivity";


    private FirebaseAuth mAuth;
    private EditText mLogin, mPassword;
    private TextView mApptext;
    private Button mLoginBtn;
    private boolean isLoginOk, isPasswordOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        isLoginOk = false;
        isPasswordOk = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = findViewById(R.id.login_et_login);
        mPassword = findViewById(R.id.login_et_password);
        mApptext = findViewById(R.id.login_greating_text);
        mLoginBtn = findViewById(R.id.login_start_button);

        mLoginBtn.setEnabled(false);
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() >=3){
                    isLoginOk = true;
                }
                else isLoginOk = false;

                if(isPasswordOk && isLoginOk){
                    mLoginBtn.setEnabled(true);
                }
                else mLoginBtn.setEnabled(false);
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

                if (isLoginOk && isPasswordOk){
                    mLoginBtn.setEnabled(true);
                }
                else mLoginBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                mAuth.signInWithEmailAndPassword(mLogin.getText().toString(), mPassword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, "LOGIN OK",
                                            Toast.LENGTH_SHORT).show();
                                    String a = user.getEmail();
                                    Toast.makeText(LoginActivity.this, a,
                                            Toast.LENGTH_SHORT).show();
                                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(mainActivity);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}