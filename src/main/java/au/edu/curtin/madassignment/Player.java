/**
 * Curtin University
 * Mobile Application Development
 * Assignment
 * Aaron Musgrave
 * 25/10/2018
 *
 * Player Class
 * Responsible for storing data about the player character
 */

package au.edu.curtin.madassignment;
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
    private LinkedList<Equipment> equipmentList;

    /* Constructor */
    Player() {
        rowLocation = 0;
        colLocation = 0;
        cash = 0;
        health = 100.0;
        equipmentMass = 0.0;
        equipmentList = new LinkedList<Equipment>();
    }

    /* Accessors */
    int getRowLocation() {
        return rowLocation;
    }

    int getColLocation() {
        return colLocation;
    }

    int getCash() {
        return cash;
    }

    double getHealth() {
        return health;
    }

    double getEquipmentMass() {
        return equipmentMass;
    }

    LinkedList<Equipment> getEquipmentList() {
        return equipmentList;
    }

    /* Mutators */
    void setRowLocation(int inRow) {
        if (inRow < 0 || inRow > GameData.MAX_ROW) {
            throw new IllegalArgumentException("Row Location must be >= 0 and <= " + GameData.MAX_ROW);
        }
        rowLocation = inRow;
    }

    void setColLocation(int inCol) {
        if (inCol < 0 || inCol > GameData.MAX_COL) {
            throw new IllegalArgumentException("Column Location must be >= 0 and <= " + GameData.MAX_COL);
        }
        colLocation = inCol;
    }

    void setCash(int inCash) {
        if (inCash < 0) {
            throw new IllegalArgumentException("Cash cannot be a negative value");
        }
        cash = inCash;
    }

    void setHealth(double inHealth) {
        if (inHealth < 0.0 || inHealth > MAX_HEALTH) {
            throw new IllegalArgumentException("Health must be >= 0.0 and <= " + MAX_HEALTH);
        }
        health = inHealth;
    }

    void setEquipmentMass(double inEquipmentMass) {
        // Considered input validation
        // However, Improbability Drive had mas of -pi and therefore means mass can be negative.
        equipmentMass = inEquipmentMass;
    }

    void setEquipmentList(LinkedList<Equipment> inEquipmentList) {
        if (inEquipmentList == null) {
            throw new IllegalArgumentException("Equipment list cannot be null");
        }
        equipmentList = inEquipmentList;
    }
}