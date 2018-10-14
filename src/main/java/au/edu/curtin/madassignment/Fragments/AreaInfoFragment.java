package au.edu.curtin.madassignment.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.*;

public class AreaInfoFragment extends Fragment {
    /* Fields */
    TextView biomeText;
    TextView descriptionText;
    TextView coordinateText;
    ImageButton starButton;
    ImageButton editButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_area_info, ui, false);

        // Reference UI Objects
        biomeText = view.findViewById(R.id.lblBiome);
        descriptionText = view.findViewById(R.id.lblDescription);
        coordinateText = view.findViewById(R.id.lblCoordinate);
        starButton = view.findViewById(R.id.btnStar);
        editButton = view.findViewById(R.id.btnEdit);

        // Set on click listeners
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Area
                Area area = GameData.getInstance().getCurrentArea();

                if (area.isStarred()) {
                    starButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    area.setStarred(false);
                    GameData.getInstance().setCurrentArea(area);
                }
                else {
                    starButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    area.setStarred(true);
                    GameData.getInstance().setCurrentArea(area);
                }
            }
        });

        update();
        return view;
    }

    public void update() {
        GameData gameData = GameData.getInstance();
        Area area = gameData.getCurrentArea();

        biomeText.setText(area.getBiomeString());
        descriptionText.setText(area.getDescription());
        coordinateText.setText(gameData.getCoordinateText());

        if (area.isStarred()) {
            starButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
        }
        else {
            starButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        }
    }
}
