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

package au.edu.curtin.madassignment;

import java.util.LinkedList;
import java.util.Random;

public class Area {
    /* Constants */
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
    Area() {
        town = false;
        itemList = new LinkedList<Item>();
        description = "";
        starred = false;
        explored = false;
    }

    /* Accessors */
    boolean isTown() {
        return town;
    }

    LinkedList<Item> getItemList() {
        return itemList;
    }

    String getDescription() {
        return description;
    }

    boolean isStarred() {
        return starred;
    }

    boolean isExplored() {
        return explored;
    }

    /* Mutators */
    void setTown(boolean inTown) {
        town = inTown;
    }

    void setItemList(LinkedList<Item> inItems) {
        itemList = inItems;
    }

    void setDescription(String inDescription) {
        description = inDescription;
    }

    void setStarred(boolean inStarred) {
        starred = inStarred;
    }

    void setExplored(boolean inExplored) {
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
