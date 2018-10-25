package au.edu.curtin.madassignment.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import au.edu.curtin.madassignment.Model.Area;
import au.edu.curtin.madassignment.Database.GameSchema.AreaTable;

public class AreaCursor extends CursorWrapper {
    public static final String TRUE = "True";

    public AreaCursor(Cursor cursor) {
        super(cursor);
    }

    public Area getArea() {
        UUID areaID = UUID.fromString(getString(getColumnIndex(AreaTable.Cols.ID)));
        int rowLocation = getInt(getColumnIndex(AreaTable.Cols.ROW_LOCATION));
        int colLocation = getInt(getColumnIndex(AreaTable.Cols.COL_LOCATION));
        boolean town = getString(getColumnIndex(AreaTable.Cols.IS_TOWN)).equals(TRUE);
        String description = getString(getColumnIndex(AreaTable.Cols.DESCRIPTION));
        boolean starred = getString(getColumnIndex(AreaTable.Cols.IS_STARRED)).equals(TRUE);
        boolean explored = getString(getColumnIndex(AreaTable.Cols.IS_EXPLORED)).equals(TRUE);

        return new Area(areaID, rowLocation, colLocation, town, description, starred, explored);
    }
}
