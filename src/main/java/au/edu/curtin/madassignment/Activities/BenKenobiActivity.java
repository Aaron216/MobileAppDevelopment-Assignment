package au.edu.curtin.madassignment.Activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import au.edu.curtin.madassignment.Model.GameData;
import au.edu.curtin.madassignment.Model.Usable.BenKenobi;
import au.edu.curtin.madassignment.R;

public class BenKenobiActivity extends AppCompatActivity {
    /* Constants */
    private static final String ITEM_ID = "edu.au.curtin.madassignment.itemid";

    /* Overrides */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_kenobi);

        // Get kenobi object
        String itemID = getIntent().getStringExtra(ITEM_ID);
        final BenKenobi kenobi = (BenKenobi) GameData.getInstance().getPlayer().getItem(itemID);

        // Get UI Objects
        VideoView kenobiVideo = findViewById(R.id.vidKenobi);
        Button useBtn = findViewById(R.id.btnUse);
        Button cancellBtn = findViewById(R.id.btnCancel);

        // Start Video
        String path = "android.resource://au.edu.curtin.madassignment/" + R.raw.kenobi_hello_there;
        kenobiVideo.setVideoPath(path);
        kenobiVideo.start();

        // Set loop
        kenobiVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        // Set On Click listeners
        useBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kenobi.confirm();
                goBack();
            }
        });

        cancellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /* Functions */
    private void goBack() {
        startActivity(InventoryActivity.getIntent(BenKenobiActivity.this, InventoryActivity.BACKPACK));
    }

    public static Intent getIntent(Context context, String itemID) {
        Intent intent = new Intent(context, BenKenobiActivity.class);
        intent.putExtra(ITEM_ID, itemID);
        return intent;
    }
}
