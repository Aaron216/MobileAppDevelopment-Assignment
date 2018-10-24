package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import au.edu.curtin.madassignment.Fragments.StatusBarFragment;
import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.R;

public class SmellActivity extends AppCompatActivity {
    /* Constants */
    private static final int SMELL_RANGE = 2;

    /* Fields */
    StatusBarFragment statusBar;
    ImageButton backButton;
    RecyclerView smellRecyclerView;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smell);

        // Get UI Objects
        backButton = findViewById(R.id.btnBack);
        smellRecyclerView = findViewById(R.id.listSmells);

        // Set On Click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Initialise Fragments
        FragmentManager fm = getSupportFragmentManager();
        statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        // Specify layout
        smellRecyclerView.setLayoutManager(new LinearLayoutManager(SmellActivity.this));

        // Populate recycler view
        SmellAdaptor adapter = new SmellAdaptor(getSmellList());
        smellRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusBar.update();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /* Functions */
    private List<Smell> getSmellList() {
        // Declare Variables
        Area currArea;
        Smell currSmell;
        List<Smell> smellList = new LinkedList<>();

        // Get Current location
        GameData gameInstance = GameData.getInstance();
        int currX = gameInstance.getPlayer().getColLocation();
        int currY = gameInstance.getPlayer().getRowLocation();

        // Get bounds
        int minX = Math.max((currX - SMELL_RANGE), 0);
        int minY = Math.max((currY - SMELL_RANGE), 0);
        int maxX = Math.min((currX + SMELL_RANGE), GameData.MAX_COL);
        int maxY = Math.min((currY + SMELL_RANGE), GameData.MAX_ROW);

        // Create list of items within smell range
        for (int yy = minY; yy <= maxY; yy++) {
            for (int xx = minX; xx <= maxX; xx++) {
                currArea = gameInstance.getArea(xx, yy);
                // For each Item in current area
                for (Item currItem : currArea.getItemList()) {
                    // Create smell object
                    currSmell = new Smell(currItem, (yy - currY), (xx - currX));
                    // Add to smell list
                    smellList.add(currSmell);
                }
            }
        }

        return smellList;
    }
    private void goBack() {
        startActivity(InventoryActivity.getIntent(SmellActivity.this, InventoryActivity.BACKPACK));
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SmellActivity.class);
    }

    /**
     * Smell Adaptor Class
     */
    private class SmellAdaptor extends RecyclerView.Adapter<SmellViewHolder> {
        /* Fields */
        private List<Smell> smellList;

        /* Constructor */
        SmellAdaptor(List<Smell> inSmells) {
            setSmellList(inSmells);
        }

        /* Mutator */
        void setSmellList(List<Smell> inSmells) {
            if (inSmells == null) {
                throw new IllegalArgumentException("Smell list cannot be null");
            }
            this.smellList = inSmells;
        }

        /* Overrides */
        @NonNull
        @Override
        public SmellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int index) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new SmellViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SmellViewHolder viewHolder, int index) {
            viewHolder.bind(smellList.get(index));
        }

        @Override
        public int getItemCount() {
            return smellList.size();
        }
    }

    /**
     * Smell View Holder Class
     */
    private class SmellViewHolder extends RecyclerView.ViewHolder {
        /* Fields */
        private TextView nameText;
        private TextView verticalText;
        private TextView horizontalText;

        /* Constructor */
        SmellViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_smell_list, parent, false));

            // Get UI element references
            // Item view is field of RecyclerView.ViewHolder
            nameText = itemView.findViewById(R.id.lblItemName);
            verticalText = itemView.findViewById(R.id.lblVertical);
            horizontalText = itemView.findViewById(R.id.lblHorizontal);
        }

        /* Functions */
        void bind(final Smell smell) {
            // Set text
            nameText.setText(smell.getItemName());
            verticalText.setText(smell.getSouthText());
            horizontalText.setText(smell.getEastText());
        }
    }
}
