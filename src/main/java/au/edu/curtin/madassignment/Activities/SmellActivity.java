package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import au.edu.curtin.madassignment.R;

public class SmellActivity extends AppCompatActivity {

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smell);
    }

    /* Functions */
    public static Intent getInent(Context context) {
        return new Intent(context, SmellActivity.class);
    }
}
