package au.edu.curtin.madassignment.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import au.edu.curtin.madassignment.*;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get references to UI objects
        Button newGameButton = findViewById(R.id.btnNewGame);
        Button continueGameButton = findViewById(R.id.btnContinue);

        // Set on click listeners
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NavigationActivity.getIntent(WelcomeActivity.this, true));
            }
        });

        continueGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NavigationActivity.getIntent(WelcomeActivity.this, false));
            }
        });
    }
}
