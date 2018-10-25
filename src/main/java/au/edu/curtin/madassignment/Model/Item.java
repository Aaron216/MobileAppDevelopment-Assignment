/*
  Curtin University
  Mobile Application Development
  Assignment
  Aaron Musgrave
  25/10/2018

  Item Abstract Class
  Responsible for storing data about an item
 */

package au.edu.curtin.madassignment.Model;

import android.content.ContentValues;

import java.util.UUID;

import au.edu.curtin.madassignment.Database.GameSchema.*;

public abstract class Item {
    /* Fields */
    private UUID itemID;
    private boolean selected;
    private String type;
    private String description;
    private int value;

    /* Constructor */
    Item() {
        this.itemID = UUID.randomUUID();
        this.selected = false;
        this.type = "";
        this.description = "";
        this.value = 0;
    }

    Item(UUID inID, String inDescription, int inValue) {
        this.itemID = inID;
        this.selected = false;
        this.type = "";
        this.description = inDescription;
        this.value = inValue;
    }

    /* Accessors */
    protected String getIDString() {
        return itemID.toString();
    }

    public boolean isSelected() {
        return selected;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        String output = "";
        output += "Type: " + type + ", ";
        output += "ID: " + itemID.toString() + ", ";
        output += "Desc: " + description;
        return output;
    }

    /* Mutators */
    public void setItemID(UUID inID) {
        this.itemID = inID;
    }

    public void toggleSelected() {
        this.selected = !this.selected;
    }

    public void setSelected(boolean inSelected) {
        this.selected = inSelected;
    }

    public void setType(String inType) {
        if ((inType == null) || (inType.isEmpty())) {
            throw new IllegalArgumentException("Type string cannot be null or empty");
        }
        this.type = inType;
    }

    public void setDescription(String inDescription) {
        this.description = inDescription;
    }

    public void setValue(int inValue) {
        if (inValue < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }
        this.value = inValue;
    }

    /* Database Functions */
    ContentValues getPlayerItemContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(PlayerItemTable.Cols.ID, getIDString());
        cv.put(PlayerItemTable.Cols.TYPE, getType());
        cv.put(PlayerItemTable.Cols.DESCRIPTION, getDescription());
        cv.put(PlayerItemTable.Cols.VALUE, getValue());

        if (this instanceof Equipment) {
            cv.put(PlayerItemTable.Cols.MASS, ((Equipment) this).getMass());
        }
        else {
            cv.put(PlayerItemTable.Cols.MASS, 0.0);
        }

        if (this instanceof Food) {
            cv.put(PlayerItemTable.Cols.HEALTH, ((Food) this).getHealth());
        }
        else {
            cv.put(PlayerItemTable.Cols.HEALTH, 0.0);
        }

        return cv;
    }

    ContentValues getAreaItemContentValues(int row, int col) {
        ContentValues cv = new ContentValues();

        cv.put(AreaItemTable.Cols.ID, getIDString());
        cv.put(AreaItemTable.Cols.ROW_LOCATION, String.valueOf(row));
        cv.put(AreaItemTable.Cols.COL_LOCATION, String.valueOf(col));
        cv.put(AreaItemTable.Cols.TYPE, getType());
        cv.put(AreaItemTable.Cols.DESCRIPTION, getDescription());
        cv.put(AreaItemTable.Cols.VALUE, getValue());

        if (this instanceof Equipment) {
            cv.put(AreaItemTable.Cols.MASS, ((Equipment) this).getMass());
        }
        else {
            cv.put(AreaItemTable.Cols.MASS, 0.0);
        }

        if (this instanceof Food) {
            cv.put(AreaItemTable.Cols.HEALTH, ((Food) this).getHealth());
        }
        else {
            cv.put(AreaItemTable.Cols.HEALTH, 0.0);
        }

        return cv;
    }
}
