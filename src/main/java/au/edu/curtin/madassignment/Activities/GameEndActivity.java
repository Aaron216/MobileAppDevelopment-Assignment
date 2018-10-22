package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.GameData;

public class GameEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        Button playAgainButton = findViewById(R.id.btnPlayAgain);
        Button quitButton = findViewById(R.id.btnQuit);

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameData.newGame();
                startActivity(NavigationActivity.getIntent(GameEndActivity.this));
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(WelcomeActivity.getIntent(GameEndActivity.this));
            }
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, GameEndActivity.class);
    }

    @Override
    public void onBackPressed() {
        startActivity(WelcomeActivity.getIntent(GameEndActivity.this));
    }
}
