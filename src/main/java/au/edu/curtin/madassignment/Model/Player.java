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

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class Player {
    /* Constants */
    private static final double MAX_HEALTH = 100.0;

    /* Fields */
    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipmentMass;
    private List<Item> itemList;
    private boolean[] hasSpecial;

    /* Constructor */
    Player() {
        rowLocation = 0;
        colLocation = 0;
        cash = 0;
        health = 100.0;
        equipmentMass = 0.0;
        itemList = new LinkedList<>();
        hasSpecial = new boolean[]{false, false, false};
    }

    /* Accessors */
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

    public boolean[] getHasSpecial() {
        return hasSpecial;
    }

    /* Mutators */
    private void setRowLocation(int inRow) {
        if (inRow < 0 || inRow > GameData.MAX_ROW) {
            throw new IllegalArgumentException("Row Location must be >= 0 and <= " + GameData.MAX_ROW);
        }
        rowLocation = inRow;
    }

    private void setColLocation(int inCol) {
        if (inCol < 0 || inCol > GameData.MAX_COL) {
            throw new IllegalArgumentException("Column Location must be >= 0 and <= " + GameData.MAX_COL);
        }
        colLocation = inCol;
    }

    public void setCash(int inCash) {
        if (inCash < 0) {
            throw new IllegalArgumentException("Cash cannot be a negative value");
        }
        cash = inCash;
    }

    public void setHealth(double inHealth) {
        if (inHealth <= 0.0) {
            health = 0.0;
            GameData.getInstance().setGameOver();
        }
        health = Math.min(inHealth, MAX_HEALTH);
    }

    private void setEquipmentMass(double inEquipmentMass) {
        // Considered input validation
        // However, Improbability Drive had mas of -pi and therefore means mass can be negative.
        equipmentMass = inEquipmentMass;
    }

    public void setItemList(List<Item> inItemList) {
        if (inItemList == null) {
            throw new IllegalArgumentException("Item list cannot be null");
        }
        itemList = inItemList;
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
    }

    void sellItems(List<Item> items) {
        setCash(cash + (int) Math.round(sumItemValue(items) * GameData.SELL_MARKDOWN));
        removeItems(items);
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

    void removeItems(List<Item> items) {
        checkSpecial(items, false);
        setEquipmentMass(equipmentMass - sumEquipmentMass(items));
        itemList.removeAll(items);
    }

    public void addItems(List<Item> items) {
        checkSpecial(items, true);
        setEquipmentMass(equipmentMass + sumEquipmentMass(items));
        itemList.addAll(items);
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

    void useItems(List<Item> items) {
        // TODO: Usable Items
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

    private void checkSpecial(List<Item> items, boolean hasItem) {
        Equipment currEquipment;
        boolean hasAll = true;
        int itemType;

        for (Item currItem : items) {
            // Check if equipment
            if (currItem instanceof Equipment) {
                currEquipment = (Equipment) currItem;
                // Check if special
                if (currEquipment.isSpecial()) {
                    itemType = Arrays.asList(GameData.SPECIAL_EQUIPMENT).indexOf(currEquipment.getDescription());
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
}
