package au.edu.curtin.madassignment.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.Model.Usable.*;
import au.edu.curtin.madassignment.Database.GameSchema.*;

public class ItemCursor extends CursorWrapper {
    public ItemCursor(Cursor cursor) {
        super(cursor);
    }

    public Item getAreaItem() {
        Item item;

        UUID itemID = UUID.fromString(getString(getColumnIndex(AreaItemTable.Cols.ID)));
        int row = getInt(getColumnIndex(AreaItemTable.Cols.ROW_LOCATION));
        int col = getInt(getColumnIndex(AreaItemTable.Cols.COL_LOCATION));
        String type = getString(getColumnIndex(AreaItemTable.Cols.TYPE));
        String description = getString(getColumnIndex(AreaItemTable.Cols.DESCRIPTION));
        int value = getInt(getColumnIndex(AreaItemTable.Cols.VALUE));
        double health = getDouble(getColumnIndex(AreaItemTable.Cols.HEALTH));
        double mass = getDouble(getColumnIndex(AreaItemTable.Cols.MASS));

        switch (type) {
            case Food.TYPE:
                item = new Food(itemID, description, value, health);
                break;
            case Equipment.TYPE:
                item = new Equipment(itemID, description, value, mass, false);
                break;
            case Equipment.SPECIAL_TYPE:
                item = new Equipment(itemID, description, value, mass, true);
                break;
            case Equipment.Usable.TYPE:

                switch (description) {
                    case BenKenobi.NAME:
                        item = new BenKenobi(itemID);
                        break;
                    case ImprobabilityDrive.NAME:
                        item = new ImprobabilityDrive(itemID);
                        break;
                    case SmellOScope.NAME:
                        item = new SmellOScope(itemID);
                        break;
                    default:
                        throw new IllegalArgumentException("Cannot read usable item from database: Unknown description");
                }
                break;
            default:
                throw new IllegalArgumentException("Cannot read item from database: Unknown type");
        }

        // System.out.println("Area Item > " + item.toString());

        return item;
    }

    public Item getPlayerItem() {
        Item item;

        UUID itemID = UUID.fromString(getString(getColumnIndex(PlayerItemTable.Cols.ID)));
        String type = getString(getColumnIndex(PlayerItemTable.Cols.TYPE));
        String description = getString(getColumnIndex(PlayerItemTable.Cols.DESCRIPTION));
        int value = getInt(getColumnIndex(PlayerItemTable.Cols.VALUE));
        double health = getDouble(getColumnIndex(PlayerItemTable.Cols.HEALTH));
        double mass = getDouble(getColumnIndex(PlayerItemTable.Cols.MASS));

        switch (type) {
            case Food.TYPE:
                item = new Food(itemID, description, value, health);
                break;
            case Equipment.TYPE:
                item = new Equipment(itemID, description, value, mass, false);
                break;
            case Equipment.SPECIAL_TYPE:
                item = new Equipment(itemID, description, value, mass, true);
                break;
            case Equipment.Usable.TYPE:

                switch (description) {
                    case BenKenobi.NAME:
                        item = new BenKenobi(itemID);
                        break;
                    case ImprobabilityDrive.NAME:
                        item = new ImprobabilityDrive(itemID);
                        break;
                    case SmellOScope.NAME:
                        item = new SmellOScope(itemID);
                        break;
                    default:
                        throw new IllegalArgumentException("Cannot read usable item from database: Unknown description");
                }
                break;
            default:
                throw new IllegalArgumentException("Cannot read item from database: Unknown type");
        }

        // System.out.println("Player Item > " + item.toString());

        return item;
    }
}
