package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
;
import au.edu.curtin.madassignment.Fragments.*;
import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.*;

public class NavigationActivity extends AppCompatActivity {
    /* Constants */
    private static final String START_NEW = "au.edu.curtin.madassigment.startNew";

    /* Fields */
    private ImageButton northButton;
    private ImageButton westButton;
    private ImageButton southButton;
    private ImageButton eastButton;
    private Button optionsButton;
    private Button inventoryButton;
    private Button overviewButton;
    private AreaInfoFragment areaInfo;
    private StatusBarFragment statusBar;

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
        inventoryButton = findViewById(R.id.btnInventory);
        overviewButton = findViewById(R.id.btnOverview);

        // Initialise Fragments
        FragmentManager fm = getSupportFragmentManager();
        areaInfo = (AreaInfoFragment) fm.findFragmentById(R.id.frameAreaInfo);
        statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);

        if (areaInfo == null) {
            areaInfo = new AreaInfoFragment();
            fm.beginTransaction().add(R.id.frameAreaInfo, areaInfo).commit();
        }

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        // Set on click listeners
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
                if (GameData.getInstance().getCurrentArea().isTown()) {
                    startActivity(InventoryActivity.getIntent(NavigationActivity.this, InventoryActivity.MARKET));
                }
                else {
                    startActivity(InventoryActivity.getIntent(NavigationActivity.this, InventoryActivity.WILDERNESS));
                }
            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(InventoryActivity.getIntent(NavigationActivity.this, InventoryActivity.BACKPACK));
            }
        });

        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(OverviewActivity.getIntent(NavigationActivity.this));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    /* Functions */
    private void move(char direction) {
        try {
            GameData.getInstance().getPlayer().move(direction);
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        update();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, NavigationActivity.class);
    }

    private void update() {
        int xx = GameData.getInstance().getPlayer().getColLocation();
        int yy = GameData.getInstance().getPlayer().getRowLocation();

        // Update UI elements
        northButton.setEnabled(yy > 0);
        southButton.setEnabled(yy < GameData.MAX_ROW);
        westButton.setEnabled(xx > 0);
        eastButton.setEnabled(xx < GameData.MAX_COL);

        if (northButton.isEnabled()) {
            northButton.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }
        else {
            northButton.setImageResource(R.drawable.ic_keyboard_arrow_up_grey_24dp);
        }

        if (southButton.isEnabled()) {
            southButton.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }
        else {
            southButton.setImageResource(R.drawable.ic_keyboard_arrow_down_grey_24dp);
        }

        if (westButton.isEnabled()) {
            westButton.setImageResource(R.drawable.ic_keyboard_arrow_left_black_24dp);
        }
        else {
            westButton.setImageResource(R.drawable.ic_keyboard_arrow_left_grey_24dp);
        }

        if (eastButton.isEnabled()) {
            eastButton.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
        }
        else {
            eastButton.setImageResource(R.drawable.ic_keyboard_arrow_right_grey_24dp);
        }

        // Area Info and Status Bar
        areaInfo.update();
        statusBar.update();
    }
}
