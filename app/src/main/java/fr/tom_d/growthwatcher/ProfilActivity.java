package fr.tom_d.growthwatcher;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import fr.tom_d.growthwatcher.api.UserHelper;
import fr.tom_d.growthwatcher.models.User;


public class ProfilActivity extends BaseActivity {

    private static final int UPDATE_USERNAME = 30;
    private FirebaseAuth mAuth;
    private TextView mUsername;
    private EditText mNewUsername;
    private ProgressBar mProgressBar;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profil);

        mUsername = findViewById(R.id.profile_tv_username);
        mNewUsername = findViewById(R.id.profile_et_username);
        mProgressBar = findViewById(R.id.profile_progressbar);
        mButton = findViewById(R.id.profile_change_username_btn);
        mProgressBar.setVisibility(View.INVISIBLE);

        this.updateUIWhenCreating();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                updateUsernameInFirebase();
            }
        });
    }




    private void updateUIWhenCreating(){

        if (this.getCurrentUser() != null){
            String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();
            this.mUsername.setText(username);
        }
        UserHelper.getUser(this.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User currentUser = documentSnapshot.toObject(User.class);
                String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
            }
        });
    }

    private void updateUsernameInFirebase(){

        String username = this.mNewUsername.getText().toString();

        if (this.getCurrentUser() != null){
            if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                UserHelper.updateUsername(username, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_USERNAME));
            }
        }
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    // 8 - Hiding Progress bar after request completed
                    case UPDATE_USERNAME:
                        mProgressBar.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
    }


}
