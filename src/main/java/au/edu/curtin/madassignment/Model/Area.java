/**
 * Curtin University
 * Mobile Application Development
 * Assignment
 * Aaron Musgrave
 * 25/10/2018
 *
 * Area Class
 * Responsible for storing data about an area
 */

package au.edu.curtin.madassignment.Model;

import java.util.LinkedList;
import java.util.Random;

public class Area {
    /* Constants */
    private static final String TOWN = "Town";
    private static final String WILDERNESS = "Wilderness";
    private static final int DICE_SIZE = 10;
    private static final int TOWN_CHANCE = 6;
    private static final int ITEM_CHANCE = 5;
    private static final int MAX_NUM_ITEMS = 10;

    /* Fields */
    private boolean town;
    private LinkedList<Item> itemList;
    private String description;
    private boolean starred;
    private boolean explored;

    /* Constructor */
    public Area() {
        town = false;
        itemList = new LinkedList<Item>();
        description = "";
        starred = false;
        explored = false;
    }

    /* Accessors */
    public boolean isTown() {
        return town;
    }

    public LinkedList<Item> getItemList() {
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
    public void setTown(boolean inTown) {
        town = inTown;
    }

    public void setItemList(LinkedList<Item> inItems) {
        itemList = inItems;
    }

    public void setDescription(String inDescription) {
        description = inDescription;
    }

    public void setStarred(boolean inStarred) {
        starred = inStarred;
    }

    public void setExplored(boolean inExplored) {
        explored = inExplored;
    }

    /* Functions */
    void randomize() {
        Random random = new Random();
        int randNum;
        int numItems;

        // Is Town
        randNum = random.nextInt(DICE_SIZE);
        town = (randNum > TOWN_CHANCE);

        // Items
        numItems = random.nextInt(MAX_NUM_ITEMS);
        for (int ii = 0; ii < numItems; ii++) {
            randNum = random.nextInt(DICE_SIZE);
            if (randNum < ITEM_CHANCE) {
                itemList.add(new Equipment());
            }
            else {
                itemList.add(new Food());
            }
        }
    }

}
