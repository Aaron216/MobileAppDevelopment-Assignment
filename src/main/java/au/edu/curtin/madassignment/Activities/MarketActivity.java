package au.edu.curtin.madassignment.Activities;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

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

        // Populate list data
        playerEquipment = GameData.getInstance().getPlayer().getEquipmentList();
        areaItems = GameData.getInstance().getCurrentArea().getItemList();

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

    public static Intent getIntent(Context context) {
        return new Intent(context, MarketActivity.class);
    }
}
