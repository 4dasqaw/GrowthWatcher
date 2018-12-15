package fr.tom_d.growthwatcher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import fr.tom_d.growthwatcher.api.UserHelper;

public class MainActivity extends BaseActivity {

    private final static int RC_SIGN_IN = 100;

    private FirebaseAuth mAuth;
    private TextView mMail, mId;
    private EditText mPlantDate, mRelDate, mTempRel;
    private Button mPlantDateBtn, mTempRelBtn, mProfilBtn;
    private GraphView mGraph;
    private DataPoint[] mDataPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMail = findViewById(R.id.main_mail);
        mId = findViewById(R.id.main_id);
        //mGraph = findViewById(R.id.main_graph);

        mPlantDate = findViewById(R.id.main_et_date_plant);
        mPlantDateBtn = findViewById(R.id.main_ok_plant_btn);

        mRelDate = findViewById(R.id.main_et_date_rel);
        mTempRel = findViewById(R.id.main_et_temp);
        mTempRelBtn = findViewById(R.id.main_ok_temp_btn);

        mProfilBtn = findViewById(R.id.main_profil_btn);

        //LineGraphSeries<DataPoint> series = new LineGraphSeries<>(mDataPoint);

        mPlantDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mProfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(profileActivity);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        String vEmail = this.getCurrentUser().getEmail();
        String vId = this.getCurrentUser().getDisplayName();
        mMail.setText(vEmail);
        mId.setText(vId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }
}
