/*
  Curtin University
  Mobile Application Development
  Assignment
  Aaron Musgrave
  25/10/2018

  Area Class
  Responsible for storing data about an area
 */

package au.edu.curtin.madassignment.Model;

import android.content.ContentValues;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import au.edu.curtin.madassignment.Model.Usable.BenKenobi;
import au.edu.curtin.madassignment.Model.Usable.ImprobabilityDrive;
import au.edu.curtin.madassignment.Model.Usable.SmellOScope;
import au.edu.curtin.madassignment.Model.GameSchema.AreaTable;

public class Area {
    /* Constants */
    private static final String TOWN = "Town";
    private static final String WILDERNESS = "Wilderness";
    private static final double TOWN_CHANCE = 0.3;
    private static final double TOWN_MODIFIER = 1.5;
    private static final int FOOD_MIN = 1;
    private static final int FOOD_RANGE = 3;
    private static final int EQUIPMENT_MIN = 1;
    private static final int EQUIPMENT_RANGE = 3;
    private static final double USABLE_CHANCE = 0.2;
    private static final int NUM_USABLE = 3;

    /* Fields */
    private UUID areaID;
    private int rowLocation;
    private int colLocation;
    private boolean town;
    private List<Item> itemList;
    private String description;
    private boolean starred;
    private boolean explored;

    /* Constructor */
    public Area() {
        areaID = UUID.randomUUID();
        rowLocation = 0;
        colLocation = 0;
        town = false;
        itemList = new LinkedList<>();
        description = "";
        starred = false;
        explored = false;
    }

    /* Accessors */
    String getIDString() {
        return areaID.toString();
    }
    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public String getCoordinateText() {
        String coordinate = "";
        coordinate += "[" + getRowLocation() + "]";
        coordinate += "[" + getColLocation() + "]";
        return coordinate;
    }

    public boolean isTown() {
        return town;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStarred() {
        return starred;
    }

    public boolean isExplored() {
        return explored;
    }

    public String getBiomeString() {
        String biome;

        if (town) {
            biome = TOWN;
        }
        else {
            biome = WILDERNESS;
        }

        return biome;
    }

    /* Mutators */
    void setRowLocation(int inRow) {
        if (inRow < 0 || inRow > GameData.MAX_ROW) {
            throw new IllegalArgumentException("Row Location must be >= 0 and <= " + GameData.MAX_ROW);
        }
        this.rowLocation = inRow;
        GameData.getInstance().dbUpdateArea(this);
    }

    void setColLocation(int inCol) {
        if (inCol < 0 || inCol > GameData.MAX_COL) {
            throw new IllegalArgumentException("Column Location must be >= 0 and <= " + GameData.MAX_COL);
        }
        this.colLocation = inCol;
        GameData.getInstance().dbUpdateArea(this);
    }

    public void setTown(boolean inTown) {
        this.town = inTown;
        GameData.getInstance().dbUpdateArea(this);
    }

    public void setItemList(List<Item> inItems) {
        if (inItems == null) {
            throw new IllegalArgumentException("Cannot set item list to null");
        }
        this.itemList = inItems;
        GameData.getInstance().dbReplaceAreaItems(rowLocation, colLocation, itemList);
    }

    void addItem(Item inItem) {
        if (inItem == null) {
            throw new IllegalArgumentException("Cannot add null item to area");
        }
        itemList.add(inItem);
        GameData.getInstance().dbAddAreaItem(rowLocation, colLocation, inItem);
    }

    void addItems(List<Item> inItems) {
        if (inItems == null) {
            throw new IllegalArgumentException("Cannot add null items to area");
        }
        itemList.addAll(inItems);
        GameData.getInstance().dbAddAreaItems(rowLocation, colLocation, inItems);
    }

    public void removeItems(List<Item> inItems) {
        itemList.removeAll(inItems);
        GameData.getInstance().dbRemoveAreaItems(inItems);
    }

    public void setDescription(String inDescription) {
        description = inDescription;
        GameData.getInstance().dbUpdateArea(this);
    }

    public void toggleStarred() {
        setStarred(!starred);
    }

    private void setStarred(boolean inStarred) {
        starred = inStarred;
        GameData.getInstance().dbUpdateArea(this);
    }

    void setExplored() {
        setExplored(true);
    }

    void setExplored(boolean inExplored) {
        explored = inExplored;
        GameData.getInstance().dbUpdateArea(this);
    }

    /* Functions */
    void randomize() {
        Random random = new Random();
        double townMod = 1.0;
        double randDouble;
        int randInt;

        // Is Town
        randDouble = random.nextDouble();
        if (randDouble < TOWN_CHANCE) {
            town = true;
            townMod = TOWN_MODIFIER;
        }

        // Food
        randInt = (int) Math.round((random.nextInt(FOOD_RANGE) + FOOD_MIN) * townMod);
        for (int ii = 0; ii < randInt; ii++) {
            itemList.add(new Food());
        }

        // Equipment
        randInt = (int) Math.round((random.nextInt(EQUIPMENT_RANGE) + EQUIPMENT_MIN) * townMod);
        for (int ii = 0; ii < randInt; ii++) {
            itemList.add(new Equipment());
        }

        // Usable
        randDouble = random.nextDouble();
        if (randDouble < USABLE_CHANCE) {
            // Add usable item
            randInt = random.nextInt(NUM_USABLE);
            switch (randInt) {
                case 0:
                    itemList.add(new BenKenobi());
                    break;
                case 1:
                    itemList.add(new ImprobabilityDrive());
                    break;
                case 2:
                    itemList.add(new SmellOScope());
                    break;
            }
        }
    }

    /* Database Functions */
    ContentValues getContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(AreaTable.Cols.ID, getIDString());
        cv.put(AreaTable.Cols.ROW_LOCATION, getRowLocation());
        cv.put(AreaTable.Cols.COL_LOCATION, getColLocation());
        cv.put(AreaTable.Cols.IS_TOWN, isTown());
        cv.put(AreaTable.Cols.DESCRIPTION, getDescription());
        cv.put(AreaTable.Cols.IS_STARRED, isStarred());
        cv.put(AreaTable.Cols.IS_EXPLORED, isExplored());

        return cv;
    }

}
