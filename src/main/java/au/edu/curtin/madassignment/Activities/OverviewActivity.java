package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;

import au.edu.curtin.madassignment.*;
import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.Fragments.*;

public class OverviewActivity extends AppCompatActivity {
    /* Fields */
    private ImageButton backButton;
    private StatusBarFragment statusBar;
    private AreaInfoFragment areaInfo;

    private RecyclerView verticalRecycler;
    private HorizontalScrollView horizontalScroll;

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        // Get references to UI objects
        backButton = findViewById(R.id.btnBack);
        verticalRecycler = findViewById(R.id.verticalRecycler);
        horizontalScroll = findViewById(R.id.horizontalScroll);

        // Initialise Fragments
        FragmentManager fm = getSupportFragmentManager();
        statusBar = (StatusBarFragment) fm.findFragmentById(R.id.frameStatusBar);
        areaInfo = (AreaInfoFragment) fm.findFragmentById(R.id.frameAreaInfo);

        if (statusBar == null) {
            statusBar = new StatusBarFragment();
            fm.beginTransaction().add(R.id.frameStatusBar, statusBar).commit();
        }

        if (areaInfo == null) {
            areaInfo = new AreaInfoFragment();
            fm.beginTransaction().add(R.id.frameAreaInfo, areaInfo).commit();
        }

        // Set on click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Set up layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(OverviewActivity.this, GameData.MAX_COL);
        layoutManager.setInitialPrefetchItemCount(GameData.MAX_COL * GameData.MAX_ROW);
        verticalRecycler.setLayoutManager(layoutManager);

        // Populate recycler view
        MapAdaptor adaptor = new MapAdaptor(GameData.getInstance().getGrid());
        verticalRecycler.setAdapter(adaptor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusBar.update();
        areaInfo.update();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /* Functions */
    private void goBack() {
        startActivity(NavigationActivity.getIntent(OverviewActivity.this));
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, OverviewActivity.class);
    }

    /**
     * Map Adaptor Class
     */
    private class MapAdaptor extends RecyclerView.Adapter<MapViewHolder> {
        /* Fields */
        private Area[][] areaGrid;

        /* Constructor */
        MapAdaptor(Area[][] inGrid) {
            setAreaGrid(inGrid);
        }

        /* Mutator */
        void setAreaGrid(Area[][] inGrid) {
            this.areaGrid = inGrid;
        }

        /* Overrides */
        @NonNull
        @Override
        public MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int index) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MapViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MapViewHolder viewHolder, int index) {
            int row = index / GameData.MAX_ROW;
            int col = index % GameData.MAX_COL;
            viewHolder.bind(areaGrid[row][col]);
        }

        @Override
        public int getItemCount() {
            return GameData.MAX_ROW * GameData.MAX_COL;
        }
    }

    /**
     * Map View Holder Class
     */
    private class MapViewHolder extends RecyclerView.ViewHolder {
        /* Fields */
        Area area;

        ConstraintLayout areaLayout;
        ImageView areaTypeImage;
        ImageView currentAreaImage;
        ImageView areaStarredImage;

        /* Constructor */
        MapViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_map_grid, parent, false));

            // Get UI element references
            areaLayout = itemView.findViewById(R.id.layoutArea);
            areaTypeImage = itemView.findViewById(R.id.imgType);
            currentAreaImage = itemView.findViewById(R.id.imgLocation);
            areaStarredImage = itemView.findViewById(R.id.imgStarred);

            // Set onclick listener
            areaLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((area != null) && (area.isExplored())) {
                        areaInfo.setArea(area);
                        areaInfo.update();
                    }
                }
            });

            // Setup horizontal scroll
            horizontalScroll.post(new Runnable() {
                @Override
                public void run() {
                    Player player = GameData.getInstance().getPlayer();

                    int viewWidth = areaLayout.getWidth();
                    int viewHeight = areaLayout.getHeight();

                    int col = player.getColLocation() * viewWidth;
                    int row = player.getRowLocation() * viewHeight;

                    horizontalScroll.scrollTo(col, row);
                }
            });
        }

        // Functions
        void bind(Area inArea) {
            area = inArea;

            // Set area type
            if (area.isExplored()) {
                if (area.isTown()) {
                    // Town
                    areaTypeImage.setImageResource(R.drawable.ic_home);
                }
                else {
                    // Wilderness
                    areaTypeImage.setImageResource(R.drawable.ic_grass);
                }
            }
            else {
                // Unknown
                areaTypeImage.setImageResource(R.drawable.ic_question);
            }

            // Set Starred
            if (area.isStarred()) {
                areaStarredImage.setVisibility(View.VISIBLE);
            }
            else {
                areaStarredImage.setVisibility(View.INVISIBLE);
            }

            // Is Current location
            Player player = GameData.getInstance().getPlayer();
            if ((area.getColLocation() == player.getColLocation()) && (area.getRowLocation() == player.getRowLocation())) {
                currentAreaImage.setVisibility(View.VISIBLE);
            }
            else {
                currentAreaImage.setVisibility(View.INVISIBLE);
            }
        }
    }
}
