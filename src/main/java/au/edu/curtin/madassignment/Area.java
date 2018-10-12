package au.edu.curtin.madassignment;

import java.util.LinkedList;

public class Area {
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

}
