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

package au.edu.curtin.madassignment.Model;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

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

    public LinkedList<Equipment> getEquipmentList() {
        return equipmentList;
    }

    /* Mutators */
    public void setRowLocation(int inRow) {
        if (inRow < 0 || inRow > GameData.MAX_ROW) {
            throw new IllegalArgumentException("Row Location must be >= 0 and <= " + GameData.MAX_ROW);
        }
        rowLocation = inRow;
    }

    public void setColLocation(int inCol) {
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
        if (inHealth < 0.0 || inHealth > MAX_HEALTH) {
            throw new IllegalArgumentException("Health must be >= 0.0 and <= " + MAX_HEALTH);
        }
        health = inHealth;
    }

    public void setEquipmentMass(double inEquipmentMass) {
        // Considered input validation
        // However, Improbability Drive had mas of -pi and therefore means mass can be negative.
        equipmentMass = inEquipmentMass;
    }

    public void setEquipmentList(LinkedList<Equipment> inEquipmentList) {
        if (inEquipmentList == null) {
            throw new IllegalArgumentException("Equipment list cannot be null");
        }
        equipmentList = inEquipmentList;
    }

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
}
