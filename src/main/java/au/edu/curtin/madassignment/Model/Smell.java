package au.edu.curtin.madassignment.Model;

public class Smell {
    /* Fields */
    private Item item;
    private int south;
    private int east;

    /* Constructor */
    public Smell(Item inItem, int inSouth, int inEast) {
        setItem(inItem);
        setSouth(inSouth);
        setEast(inEast);
    }

    /* Accessors */
    public Item getItem() {
        return item;
    }

    public String getItemName() {
        return getItem().getDescription();
    }

    public int getSouth() {
        return south;
    }

    public int getEast() {
        return east;
    }

    public String getSouthText() {
        String southText = Integer.toString(Math.abs(south));

        // Label 0 with North
        if (south <= 0) {
            southText += " North";
        }
        else {
            southText += " South";
        }

        return southText;
    }

    public String getEastText() {
        String eastText = Integer.toString(Math.abs(east));

        // Label 0 with East
        if (east >= 0) {
            eastText += " East";
        }
        else {
            eastText += " West";
        }

        return eastText;
    }

    /* Mutators */
    public void setItem(Item inItem) {
        if (inItem == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        this.item = inItem;
    }

    public void setSouth(int inSouth) {
        if (Math.abs(inSouth) > GameData.MAX_ROW) {
            throw new IllegalArgumentException("North/South distance cannot be larger than map");
        }
        this.south = inSouth;
    }

    public void setEast(int inEast) {
        if (Math.abs(inEast) > GameData.MAX_COL) {
            throw new IllegalArgumentException("East/West distance cannot be larger than map");
        }
        this.east = inEast;
    }
}
