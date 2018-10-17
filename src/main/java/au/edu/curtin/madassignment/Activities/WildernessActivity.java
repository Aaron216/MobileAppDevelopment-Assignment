package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Fragments.StatusBarFragment;

public class WildernessActivity extends AppCompatActivity {
    /* Fields */
    private StatusBarFragment statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilderness);

        // Initialise Fragment
        FragmentManager fm = getSupportFragmentManager();
        statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, WildernessActivity.class);
    }
}
