package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import au.edu.curtin.madassignment.Fragments.*;
import au.edu.curtin.madassignment.R;

public class InventoryActivity extends AppCompatActivity implements ListFragment.OnActionListener{
    /* Constants */
    private static final String INVENTORY_TYPE = "au.edu.curtin.madassignment.inventorytype";
    public static final int MARKET = 1;
    public static final int WILDERNESS = 2;
    public static final int BACKPACK = 3;

    /* Fields */
    private int type;
    private TextView headerText;
    private StatusBarFragment statusBar;
    private ListFragment leftList;
    private ListFragment rightList;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Get intent
        Intent intent = getIntent();
        type = intent.getIntExtra(INVENTORY_TYPE, 0);
        if (type < MARKET || type > BACKPACK) {
            throw new IllegalArgumentException("Unknown Inventory Type");
        }

        // Get References
        headerText = findViewById(R.id.lblHeader);

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
        leftList = (ListFragment) fm.findFragmentById(R.id.frameLeftList);
        rightList = (ListFragment) fm.findFragmentById(R.id.frameRightList);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        if (leftList == null) {
            leftList = new ListFragment();
            fm.beginTransaction().add(R.id.frameLeftList, leftList).commit();
        }

        if (rightList == null) {
            rightList = new ListFragment();
            fm.beginTransaction().add(R.id.frameRightList, rightList).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set list types
        switch (type) {
            case MARKET:
                headerText.setText(getResources().getText(R.string.market));
                leftList.setListType(ListFragment.MARKET_SELL);
                rightList.setListType(ListFragment.MARKET_BUY);
                break;

            case WILDERNESS:
                headerText.setText(getResources().getText(R.string.wilderness));
                leftList.setListType(ListFragment.WILDERNESS_DROP);
                rightList.setListType(ListFragment.WILDERNESS_PICK);
                break;

            case BACKPACK:
                headerText.setText(getResources().getText(R.string.backpack));
                leftList.setListType(ListFragment.BACKPACK_FOOD);
                rightList.setListType(ListFragment.BACKPACK_EQUIPMENT);
                break;
        }

        update();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /* Functions */
    public void onAction() {
        update();
    }

    private void update() {
        statusBar.update();
        leftList.update();
        rightList.update();
    }

    private void goBack() {
        leftList.clearSelected();
        rightList.clearSelected();
        startActivity(NavigationActivity.getIntent(InventoryActivity.this));
    }

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, InventoryActivity.class);
        intent.putExtra(INVENTORY_TYPE, type);
        return intent;
    }
}
