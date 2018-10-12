package au.edu.curtin.madassignment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NavigationActivity extends AppCompatActivity {
    /* Fields */
    private ImageButton northButton;
    private ImageButton westButton;
    private ImageButton southButton;
    private ImageButton eastButton;
    private Button optionsButton;
    private Button overviewButton;

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
        AreaInfoFragment areaInfo = (AreaInfoFragment) fm.findFragmentById(R.id.frameAreaInfo);
        StatusBarFragment statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);

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

            }
        });

        westButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        southButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        eastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}
