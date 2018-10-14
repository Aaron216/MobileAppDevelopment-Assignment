package au.edu.curtin.madassignment.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
;
import au.edu.curtin.madassignment.Fragments.*;
import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.*;

public class NavigationActivity extends AppCompatActivity {
    /* Fields */
    private ImageButton northButton;
    private ImageButton westButton;
    private ImageButton southButton;
    private ImageButton eastButton;
    private Button optionsButton;
    private Button overviewButton;
    private Fragment areaInfo;
    private Fragment statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Get references to UI objects
        northButton = findViewById(R.id.btnNorth);
        westButton = findViewById(R.id.btnWest);
        southButton = findViewById(R.id.btnSouth);
        eastButton = findViewById(R.id.btnEast);
        optionsButton = findViewById(R.id.btnOptions);
        overviewButton = findViewById(R.id.btnOverview);

        // Initialise Fragments
        FragmentManager fm = getSupportFragmentManager();
        areaInfo = fm.findFragmentById(R.id.frameAreaInfo);
        statusBar = fm.findFragmentById(R.id.frameStatusBar);

        if (areaInfo == null) {
            areaInfo = new AreaInfoFragment();
            fm.beginTransaction().add(R.id.frameAreaInfo, areaInfo).commit();
        }

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        // Set onclick listeners
        northButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move('N');
            }
        });

        southButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move('S');
            }
        });

        westButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move('W');
            }
        });

        eastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move('E');
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    /* Functions */
    private void move(char direction) {
        Player player = GameData.getInstance().getPlayer();
        player.move(direction);
        int xx = player.getColLocation();
        int yy = player.getRowLocation();

        // Update UI elements
        northButton.setEnabled(yy < GameData.MAX_ROW);
        southButton.setEnabled(yy > 0);
        westButton.setEnabled(xx > 0);
        eastButton.setEnabled(xx < GameData.MAX_COL);

        // Area Info and Status Bar
    }
}
