package fr.tom_d.growthwatcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.tom_d.growthwatcher.api.UserHelper;

public abstract class BaseActivity extends AppCompatActivity {

    private final static int RC_SIGN_IN = 100;

    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected OnFailureListener onFailureListener() {
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), (getString(R.string.error)+ e), Toast.LENGTH_LONG).show();
            }
        };
    }

    protected void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                this.createUserInFirestore();
            } else {
            }
        }
    }

    protected void createUserInFirestore(){

        if (this.getCurrentUser() != null){

            String username = this.getCurrentUser().getDisplayName();
            String uid = this.getCurrentUser().getUid();

            UserHelper.createUser(uid, username).addOnFailureListener(this.onFailureListener());
        }
    }
}