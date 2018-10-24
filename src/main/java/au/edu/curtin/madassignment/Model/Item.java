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
    private boolean selected;
    private String type;
    private String description;
    private int value;

    /* Constructor */
    Item() {
        selected = false;
        type = "";
        description = "";
        value = 0;
    }

    /* Accessors */
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

    /* Mutators */
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
}
