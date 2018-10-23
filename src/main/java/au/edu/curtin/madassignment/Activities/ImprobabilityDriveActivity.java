package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import au.edu.curtin.madassignment.R;

public class ImprobabilityDriveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improbability_drive);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, ImprobabilityDriveActivity.class);
    }
}
