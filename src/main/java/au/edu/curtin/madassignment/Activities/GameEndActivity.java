package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Fragments.StatusBarFragment;
import au.edu.curtin.madassignment.Model.GameData;

public class GameEndActivity extends AppCompatActivity {

    /* Fields */
    StatusBarFragment statusBar;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        // Get UI elements
        TextView gameEndText = findViewById(R.id.lblGameEnd);
        Button playAgainButton = findViewById(R.id.btnPlayAgain);
        Button quitButton = findViewById(R.id.btnQuit);

        // Initialise Fragments
        FragmentManager fm = getSupportFragmentManager();
        statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        // Set on click listeners
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameData.getInstance().newGame();
                startActivity(NavigationActivity.getIntent(GameEndActivity.this));
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(WelcomeActivity.getIntent(GameEndActivity.this));
            }
        });

        // Set header
        if (GameData.getInstance().isGameOver()) {
            gameEndText.setText(getResources().getText(R.string.game_over));
            gameEndText.setTextColor(getResources().getColor(R.color.colorRed, null));
        }
        else if (GameData.getInstance().isGameWon()){
            gameEndText.setText(getResources().getText(R.string.game_won));
            gameEndText.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusBar.update();
    }

    @Override
    public void onBackPressed() {
        startActivity(WelcomeActivity.getIntent(GameEndActivity.this));
    }

    /* Functions */
    public static Intent getIntent(Context context) {
        return new Intent(context, GameEndActivity.class);
    }
}
