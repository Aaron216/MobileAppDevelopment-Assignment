/**
 * Curtin University
 * Mobile Application Development
 * Assignment
 * Aaron Musgrave
 * 25/10/2018
 *
 * Item Abstract Class
 * Responsible for storing data about an item
 */

package au.edu.curtin.madassignment;

public abstract class Item {
    /* Fields */
    private String description;
    private int value;

    /* Constructor */
    Item() {
        description = "";
        value = 0;
    }

    /* Accessors */
    String getDescription() {
        return description;
    }

    int getValue() {
        return value;
    }

    /* Mutators */
    void setDescription(String inDescription) {
        description = inDescription;
    }

    void setValue(int inValue) {
        if (inValue < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }
        value = inValue;
    }
}
