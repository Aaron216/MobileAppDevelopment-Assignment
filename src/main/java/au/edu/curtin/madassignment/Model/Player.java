/*
  Curtin University
  Mobile Application Development
  Assignment
  Aaron Musgrave
  25/10/2018

  Player Class
  Responsible for storing data about the player character
 */

package au.edu.curtin.madassignment.Model;

import android.content.ContentValues;
import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.UUID;

import au.edu.curtin.madassignment.Database.GameSchema.PlayerTable;

public class Player {
    /* Constants */
    private static final double MAX_HEALTH = 100.0;

    /* Fields */
    private UUID playerID;
    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipmentMass;
    private List<Item> itemList;
    private boolean[] hasSpecial;

    /* Constructor */
    Player() {
        this.playerID = UUID.randomUUID();
        this.rowLocation = 0;
        this.colLocation = 0;
        this.cash = 0;
        this.health = 100.0;
        this.equipmentMass = 0.0;
        this.itemList = new LinkedList<>();
        this.hasSpecial = new boolean[]{false, false, false};
    }

    public Player(UUID inID, int inRow, int inCol, int inCash, double inHealth, double inMass, boolean[] inSpecial) {
        this.playerID = inID;
        this.rowLocation = inRow;
        this.colLocation = inCol;
        this.cash = inCash;
        this.health = inHealth;
        this.equipmentMass = inMass;
        this.hasSpecial = inSpecial;
        this.itemList = new LinkedList<>();
    }

    /* Accessors */
    String getIDString() {
        return playerID.toString();
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public int getCash() {
        return cash;
    }

    public double getHealth() {
        return health;
    }

    public double getEquipmentMass() {
        return equipmentMass;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<Item> getFoodItemList() {
        List<Item> foodList = new LinkedList<>();

        for (Item currItem : itemList) {
            if (currItem instanceof Food) {
                foodList.add(currItem);
            }
        }

        return foodList;
    }

    public List<Item> getEquipmentItemList() {
        List<Item> equipmentList = new LinkedList<>();

        for (Item currItem : itemList) {
            if (currItem instanceof Equipment) {
                equipmentList.add(currItem);
            }
        }

        return equipmentList;
    }

    public Item getItem(String itemID) {
        Item item = null;

        for (Item currItem : itemList) {
            if (currItem.getIDString().equals(itemID)) {
                item = currItem;
                break;
            }
        }

        return item;
    }

    public boolean[] getHasSpecial() {
        return hasSpecial;
    }

    /* Mutators */
    private void setRowLocation(int inRow) {
        if (inRow < 0 || inRow >= GameData.MAX_ROW) {
            throw new IllegalArgumentException("Row Location must be >= 0 and < " + GameData.MAX_ROW);
        }
        rowLocation = inRow;
        GameData.getInstance().dbUpdatePlayer(this);
    }

    private void setColLocation(int inCol) {
        if (inCol < 0 || inCol >= GameData.MAX_COL) {
            throw new IllegalArgumentException("Column Location must be >= 0 and < " + GameData.MAX_COL);
        }
        colLocation = inCol;
        GameData.getInstance().dbUpdatePlayer(this);
    }

    public void setCash(int inCash) {
        if (inCash < 0) {
            throw new IllegalArgumentException("Cash cannot be a negative value");
        }
        cash = inCash;
        GameData.getInstance().dbUpdatePlayer(this);
    }

    public void setHealth(double inHealth) {
        if (inHealth <= 0.0) {
            health = 0.0;
            GameData.getInstance().setGameOver();
        }
        health = Math.min(inHealth, MAX_HEALTH);
        GameData.getInstance().dbUpdatePlayer(this);
    }

    private void setEquipmentMass(double inEquipmentMass) {
        // Considered input validation
        // However, Improbability Drive had mas of -pi and therefore means mass can be negative.
        equipmentMass = inEquipmentMass;
        GameData.getInstance().dbUpdatePlayer(this);
    }

    public void setItemList(List<Item> inItemList, boolean updateDatabase) {
        if (inItemList == null) {
            throw new IllegalArgumentException("Item list cannot be null");
        }
        itemList = inItemList;
        if (updateDatabase) {
            GameData.getInstance().dbReplacePlayerItems(inItemList);
        }
    }

    public void addItems(List<Item> items) {
        checkForSpecialItems(items, true);
        setEquipmentMass(this.equipmentMass + sumEquipmentMass(items));
        this.itemList.addAll(items);
        GameData.getInstance().dbAddPlayerItems(items);
    }

    void removeItems(List<Item> items) {
        checkForSpecialItems(items, false);
        setEquipmentMass(this.equipmentMass - sumEquipmentMass(items));
        this.itemList.removeAll(items);
        GameData.getInstance().dbRemovePlayerItems(items);
    }

    public void removeItem(Item item) {
        // Check if special item
        Equipment equipment;
        int itemType;

        // Check if equipment
        if (item instanceof Equipment) {
            equipment = (Equipment) item;

            // Check if special
            if (equipment.isSpecial()) {
                itemType = Arrays.asList(Equipment.SPECIAL_NAMES).indexOf(equipment.getDescription());
                this.hasSpecial[itemType] = false;
            }

            // Update equipment mass
            setEquipmentMass(this.equipmentMass - equipment.getMass());
        }

        // Remove from list
        this.itemList.remove(item);
        GameData.getInstance().dbRemovePlayerItem(item);
    }

    /* Functions */
    public void move(char direction) {
        switch (direction) {
            case 'N':
                // Move North
                setRowLocation(rowLocation - 1);
                break;
            case 'W':
                // Move West
                setColLocation(colLocation - 1);
                break;
            case 'E':
                // Move East
                setColLocation(colLocation + 1);
                break;
            case 'S':
                setRowLocation(rowLocation + 1);
                // Move South
                break;
            default:
                throw new IllegalArgumentException("Unknown direction argument");
        }
        setHealth(Math.max(0.0, health - 5.0 - (equipmentMass / 2.0)));
        GameData.getInstance().getCurrentArea().setExplored();
    }

    void buyItems(List<Item> items) {
        try {
            setCash(cash - sumItemValue(items));
            addItems(items);
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Not enough money to buy the selected items");
        }
    }

    void sellItems(List<Item> items) {
        setCash(cash + (int) Math.round(sumItemValue(items) * GameData.SELL_MARKDOWN));
        removeItems(items);
    }

    void eatItems(List<Item> items) {
        Food currFood;
        double sumHealth = 0.0;

        // Sum Food health
        for (Item currItem : items) {
            if (currItem instanceof Food) {
                currFood = (Food) currItem;
                sumHealth += currFood.getHealth();
            }
            else {
                throw new IllegalArgumentException("Cannot eat non-food item: " + currItem.getDescription());
            }
        }

        setHealth(health + sumHealth);
        removeItems(items);
    }

    void useItems(Context context, List<Item> items) {
        // Error checking
        if (items.size() != 1) {
            throw new IllegalArgumentException("Can only use one item at a time.");
        }
//        if (items.get(0) instanceof Equipment.Usable) {
//            throw new IllegalArgumentException("Cannot use non equipment item: " + items.get(0).getDescription());
//        }

        Equipment equipment = (Equipment) items.get(0);
        equipment.use(context);
    }

    /* Private Functions */
    private int sumItemValue(List<Item> items) {
        int valueSum = 0;

        for (Item currItem : items) {
            valueSum += currItem.getValue();
        }

        return valueSum;
    }

    private double sumEquipmentMass(List<Item> items) {
        Equipment currEquipment;
        double massSum = 0.0;

        for (Item currItem : items) {
            if (currItem instanceof Equipment) {
                currEquipment = (Equipment) currItem;
                massSum += currEquipment.getMass();
            }
        }

        return massSum;
    }

    private void checkForSpecialItems(List<Item> items, boolean hasItem) {
        Equipment currEquipment;
        boolean hasAll = true;
        int itemType;

        for (Item currItem : items) {
            // Check if equipment
            if (currItem instanceof Equipment) {
                currEquipment = (Equipment) currItem;
                // Check if special
                if (currEquipment.isSpecial()) {
                    itemType = Arrays.asList(Equipment.SPECIAL_NAMES).indexOf(currEquipment.getDescription());
                    hasSpecial[itemType] = hasItem;
                }
            }
        }

        if (hasItem) {
            // Iterate through has special array to check if has all items
            for (boolean hasThisSpecial : hasSpecial) {
                hasAll &= hasThisSpecial;
            }

            if (hasAll) {
                GameData.getInstance().setGameWon();
            }
        }
    }

    /* Database Functions */
    ContentValues getContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(PlayerTable.Cols.ID, getIDString());
        cv.put(PlayerTable.Cols.ROW_LOCATION, getRowLocation());
        cv.put(PlayerTable.Cols.COL_LOCATION, getColLocation());
        cv.put(PlayerTable.Cols.CASH, getCash());
        cv.put(PlayerTable.Cols.HEALTH, getHealth());
        cv.put(PlayerTable.Cols.EQUIPMENT_MASS, getEquipmentMass());
        cv.put(PlayerTable.Cols.HAS_JADE_MONKEY, getHasSpecial()[0]);
        cv.put(PlayerTable.Cols.HAS_ROADMAP, getHasSpecial()[1]);
        cv.put(PlayerTable.Cols.HAS_ICE_SCRAPER, getHasSpecial()[2]);

        return cv;
    }
}
