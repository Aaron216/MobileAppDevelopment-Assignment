package au.edu.curtin.madassignment.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.Player;

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

    }
}
