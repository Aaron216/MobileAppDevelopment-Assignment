package au.edu.curtin.madassignment.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import au.edu.curtin.madassignment.Model.Player;
import au.edu.curtin.madassignment.Database.GameSchema.PlayerTable;

public class PlayerCursor extends CursorWrapper {
    private static final String TRUE = "True";

    public PlayerCursor(Cursor cursor) {
        super(cursor);
    }

    public Player getPlayer() {
        UUID playerID = UUID.fromString(getString(getColumnIndex(PlayerTable.Cols.ID)));
        int rowLocation = getInt(getColumnIndex(PlayerTable.Cols.ROW_LOCATION));
        int colLocation = getInt(getColumnIndex(PlayerTable.Cols.COL_LOCATION));
        int cash = getInt(getColumnIndex(PlayerTable.Cols.CASH));
        double health = getDouble(getColumnIndex(PlayerTable.Cols.HEALTH));
        double equipmentMass = getInt(getColumnIndex(PlayerTable.Cols.EQUIPMENT_MASS));

        boolean[] hasSpecial = new boolean[3];
        hasSpecial[0] = getString(getColumnIndex(PlayerTable.Cols.HAS_JADE_MONKEY)).equals(TRUE);
        hasSpecial[1] = getString(getColumnIndex(PlayerTable.Cols.HAS_ROADMAP)).equals(TRUE);
        hasSpecial[2] = getString(getColumnIndex(PlayerTable.Cols.HAS_ICE_SCRAPER)).equals(TRUE);

        return new Player(playerID, rowLocation, colLocation, cash, health, equipmentMass, hasSpecial);
    }
}
