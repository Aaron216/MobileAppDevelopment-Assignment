package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.GameData;

public class WelcomeActivity extends AppCompatActivity {
    /* Fields */
    Button newGameButton;
    Button continueGameButton;

    GameData gameInstance;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get game instance
        gameInstance = GameData.getInstance(WelcomeActivity.this);

        // Load from database
        if (gameInstance.dbCheckForPlayer()) {
            try {
                gameInstance.dbLoadGame();
            }
            catch (Exception ex) {
                Toast.makeText(WelcomeActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        // Get references to UI objects
        newGameButton = findViewById(R.id.btnNewGame);
        continueGameButton = findViewById(R.id.btnContinue);

        // Set on click listeners
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameInstance.newGame();
                startActivity(NavigationActivity.getIntent(WelcomeActivity.this));
            }
        });

        continueGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NavigationActivity.getIntent(WelcomeActivity.this));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        continueGameButton.setEnabled(gameInstance.isGameInProgress());
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
