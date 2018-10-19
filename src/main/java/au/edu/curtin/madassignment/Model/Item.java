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
    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    /* Mutators */
    public void setDescription(String inDescription) {
        description = inDescription;
    }

    public void setValue(int inValue) {
        if (inValue < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }
        value = inValue;
    }

    /* Function */
    public void use() {

    }
}
