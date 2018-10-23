package au.edu.curtin.madassignment.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Activities.*;
import au.edu.curtin.madassignment.Model.*;

public class StatusBarFragment extends Fragment {
    /* Fields */
    TextView cashText;
    TextView healthText;
    TextView massText;
    ImageButton resetButton;
    ImageView monkeyImage;
    ImageView mapImage;
    ImageView iceImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_status_bar, ui, false);

        // Reference UI Objects
        cashText = view.findViewById(R.id.valCash);
        healthText = view.findViewById(R.id.valHealth);
        massText = view.findViewById(R.id.valMass);
        resetButton = view.findViewById(R.id.btnReset);
        monkeyImage = view.findViewById(R.id.imgMonkey);
        mapImage = view.findViewById(R.id.imgMap);
        iceImage = view.findViewById(R.id.imgIce);

        // Set up on click listeners
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Restart Game");
                builder.setMessage("Are you sure you want to restart?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Start new Game
                        GameData.newGame();
                        startActivity(NavigationActivity.getIntent(getContext()));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                });

                builder.show();
            }
        });

        return view;
    }

    public void update() {
        Player player = GameData.getInstance().getPlayer();

        if (GameData.getInstance().isGameEnd()) {
            startActivity(GameEndActivity.getIntent(getContext()));
        }

        cashText.setText(String.format(Locale.ENGLISH, "$%d", player.getCash()));
        healthText.setText(String.format(Locale.ENGLISH, "%.2f HP", player.getHealth()));
        massText.setText(String.format(Locale.ENGLISH, "%.2f kg", player.getEquipmentMass()));

        // Jade Monkey
        if (player.getHasSpecial()[1]) {
            mapImage.setImageResource(R.drawable.ic_monkey_black_24dp);
        }
        else {
            mapImage.setImageResource(R.drawable.ic_monkey_grey_24dp);
        }

        // Roadmap
        if (player.getHasSpecial()[1]) {
            mapImage.setImageResource(R.drawable.ic_map_black_24dp);
        }
        else {
            mapImage.setImageResource(R.drawable.ic_map_grey_24dp);
        }

        // Ice Scraper
        if (player.getHasSpecial()[2]) {
            mapImage.setImageResource(R.drawable.ic_map_black_24dp);
        }
        else {
            mapImage.setImageResource(R.drawable.ic_ice_grey_24dp);
        }
    }
}
