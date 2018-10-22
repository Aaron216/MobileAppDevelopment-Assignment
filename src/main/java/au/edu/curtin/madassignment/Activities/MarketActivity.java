package au.edu.curtin.madassignment.Activities;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import au.edu.curtin.madassignment.Fragments.*;
import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.R;

public class MarketActivity extends AppCompatActivity implements ListFragment.OnActionListener{
    /* Fields */
    private StatusBarFragment statusBar;
    private ListFragment buyList;
    private ListFragment sellList;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

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
        buyList = (ListFragment) fm.findFragmentById(R.id.frameBuyList);
        sellList = (ListFragment) fm.findFragmentById(R.id.frameSellList);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        if (buyList == null) {
            buyList = new ListFragment();
            fm.beginTransaction().add(R.id.frameBuyList, buyList).commit();
        }

        if (sellList == null) {
            sellList = new ListFragment();
            fm.beginTransaction().add(R.id.frameSellList, sellList).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update Status Bar
        statusBar.update();

        // Set button text
        buyList.setListType(ListFragment.MARKET_BUY);
        sellList.setListType(ListFragment.MARKET_SELL);

        // Populate list
        GameData gameInstance = GameData.getInstance();
        buyList.setData(gameInstance.getCurrentArea().getItemList());
        sellList.setData(gameInstance.getPlayer().getItemList());
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /* Functions */
    public static Intent getIntent(Context context) {
        return new Intent(context, MarketActivity.class);
    }

    public void onAction() {
        statusBar.update();
        buyList.update();
        sellList.update();
    }

    private void goBack() {
        buyList.clearSelected();
        sellList.clearSelected();
        startActivity(NavigationActivity.getIntent(MarketActivity.this));
    }
}
