package au.edu.curtin.madassignment.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.Area;
import au.edu.curtin.madassignment.Model.GameData;
import au.edu.curtin.madassignment.Model.Player;

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

        return view;
    }

    public void update() {
        Area area = GameData.getInstance().getCurrentArea();

    }
}
