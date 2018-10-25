package au.edu.curtin.madassignment.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.*;

public class AreaInfoFragment extends Fragment {
    /**
     * Area Info Interface
     */
    public interface OnActionListener {
        void update();
    }

    /* Fields */
    private OnActionListener actionListener;
    private Area area;
    private TextView biomeText;
    private TextView descriptionText;
    private TextView coordinateText;
    private ImageButton starButton;
    private ImageButton editButton;

    /* Overrides */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
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
                area.toggleStarred();

                if (area.isStarred()) {
                    starButton.setImageResource(R.drawable.ic_star_black_24dp);
                }
                else {
                    starButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                }

                actionListener.update();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Area Description");

                // Set up input edit text
                final EditText inputText = new EditText(getContext());
                inputText.setInputType(InputType.TYPE_CLASS_TEXT);
                inputText.setText(area.getDescription());
                builder.setView(inputText);

                // Set up buttons
                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newDescription = inputText.getText().toString();
                        area.setDescription(newDescription);
                        descriptionText.setText(newDescription);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                // Show Dialog
                builder.show();
            }
        });

        //update();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.actionListener = (OnActionListener)context;
        }
        catch (final ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement OnActionListener");
        }
    }

    /* Mutators */
    public void setArea(Area inArea) {
        if (inArea == null) {
            throw new IllegalArgumentException("Area cannot be null");
        }
        this.area = inArea;
    }

    /* Functions */
    public void update() {
        // Check that area is set
        if (area == null) {
            area = GameData.getInstance().getCurrentArea();
        }

        biomeText.setText(area.getBiomeString());
        descriptionText.setText(area.getDescription());
        coordinateText.setText(area.getCoordinateText());

        if (area.isStarred()) {
            starButton.setImageResource(R.drawable.ic_star_black_24dp);
        }
        else {
            starButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }
}
