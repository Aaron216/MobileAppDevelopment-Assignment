package au.edu.curtin.madassignment.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_status_bar, ui, false);

        // Reference UI Objects
        cashText = view.findViewById(R.id.valCash);
        healthText = view.findViewById(R.id.valHealth);
        massText = view.findViewById(R.id.valMass);
        resetButton = view.findViewById(R.id.btnReset);

        // Set up on click listeners
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset game state
            }
        });

        return view;
    }

    public void update() {
        Player player = GameData.getInstance().getPlayer();

        if (GameData.getInstance().isGameOver()) {
            startActivity(GameOverActivity.getIntent(getContext()));
        }

        cashText.setText(String.format(Locale.ENGLISH, "$%d", player.getCash()));
        healthText.setText(String.format(Locale.ENGLISH, "%.2f HP", player.getHealth()));
        massText.setText(String.format(Locale.ENGLISH, "%.2f kg", player.getEquipmentMass()));
    }
}
