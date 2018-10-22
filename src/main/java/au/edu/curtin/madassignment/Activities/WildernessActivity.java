package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import au.edu.curtin.madassignment.Fragments.*;
import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.R;

public class WildernessActivity extends AppCompatActivity implements ListFragment.OnActionListener{
    /* Fields */
    private StatusBarFragment statusBar;
    private ListFragment pickUpList;
    private ListFragment dropList;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilderness);

        // Set on click listeners
        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Initialise Fragments
        FragmentManager fm = getSupportFragmentManager();
        statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);
        pickUpList = (ListFragment) fm.findFragmentById(R.id.framePickUpList);
        dropList = (ListFragment) fm.findFragmentById(R.id.frameDropList);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        if (pickUpList == null) {
            pickUpList = new ListFragment();
            fm.beginTransaction().add(R.id.framePickUpList, pickUpList).commit();
        }

        if (dropList == null) {
            dropList = new ListFragment();
            fm.beginTransaction().add(R.id.frameDropList, dropList).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update status bar
        statusBar.update();

        // Set button text
        pickUpList.setListType(ListFragment.WILDERNESS_PICK);
        dropList.setListType(ListFragment.WILDERNESS_DROP);

        // Populate list
        GameData gameInstance = GameData.getInstance();
        pickUpList.setData(gameInstance.getCurrentArea().getItemList());
        dropList.setData(gameInstance.getPlayer().getItemList());
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /* Functions */
    public static Intent getIntent(Context context) {
        return new Intent(context, WildernessActivity.class);
    }

    public void onAction() {
        pickUpList.update();
        dropList.update();
    }

    private void goBack() {
        startActivity(NavigationActivity.getIntent(WildernessActivity.this, false));
    }
}
