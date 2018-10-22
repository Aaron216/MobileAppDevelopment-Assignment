package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.GameData;

public class WelcomeActivity extends AppCompatActivity {
    /* Fields */
    Button newGameButton;
    Button continueGameButton;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get references to UI objects
        newGameButton = findViewById(R.id.btnNewGame);
        continueGameButton = findViewById(R.id.btnContinue);

        // Set on click listeners
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameData.newGame();
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

    @Override
    protected void onResume() {
        super.onResume();

        // Check if there is a game to continue
        continueGameButton.setEnabled(GameData.hasInstance());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /* Functions */
    public static Intent getIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }


}
