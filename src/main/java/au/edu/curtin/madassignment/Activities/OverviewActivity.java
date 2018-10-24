package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Fragments.*;

public class OverviewActivity extends AppCompatActivity {
    /* Fields */
    private ImageButton backButton;
    private StatusBarFragment statusBar;
    private AreaInfoFragment areaInfo;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Get references to UI objects
        backButton = findViewById(R.id.btnBack);

        // Initialise Fragments
        FragmentManager fm = getSupportFragmentManager();
        statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);
        areaInfo = (AreaInfoFragment) fm.findFragmentById(R.id.frameAreaInfo);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        if (areaInfo == null) {
            areaInfo = new AreaInfoFragment();
            fm.beginTransaction().add(R.id.frameAreaInfo, areaInfo).commit();
        }

        // Set on click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /* Functions */
    private void goBack() {
        startActivity(NavigationActivity.getIntent(OverviewActivity.this));
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, OverviewActivity.class);
    }
}
