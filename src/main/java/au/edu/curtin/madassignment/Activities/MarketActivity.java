package au.edu.curtin.madassignment.Activities;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.List;

import au.edu.curtin.madassignment.Fragments.*;
import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.R;

public class MarketActivity extends AppCompatActivity {
    /* Fields */
    private StatusBarFragment statusBar;
    private ListFragment buyList;
    private ListFragment sellList;

    private LinkedList<Equipment> playerEquipment;
    private LinkedList<Item> areaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        // Set on click listeners
        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(NavigationActivity.getIntent(MarketActivity.this, false));
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
        List<Item> areaItems = gameInstance.getCurrentArea().getItemList();
        List<Item> playerItems = gameInstance.getPlayer().getItemList();

        buyList.setData(areaItems);
        sellList.setData(playerItems);
    }

    @Override
    public void onBackPressed() {
        startActivity(NavigationActivity.getIntent(MarketActivity.this, false));
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MarketActivity.class);
    }
}
