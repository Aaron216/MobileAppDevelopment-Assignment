/**
 * Curtin University
 * Mobile Application Development
 * Assignment
 * Aaron Musgrave
 * 25/10/2018
 *
 * Game Data Class
 * Responsible for storing data about the current game
 */

package au.edu.curtin.madassignment.Model;

import java.util.LinkedList;

import au.edu.curtin.madassignment.Fragments.ListFragment;

public class GameData {
    /* Constants */
    public static final double SELL_MARKDOWN = 0.75;
    public static final int MAX_ROW = 10;
    public static final int MAX_COL = 10;

    /* Fields */
    private Area[][] grid;
    private Player player;
    private boolean gameOver;
    private static GameData instance;

    /* Constructor */
    private GameData() {
        grid = new Area[MAX_ROW+1][MAX_COL+1];
        player = new Player();
        gameOver = false;
        generateMap();
    }

    /* Accessors */
    public Area[][] getGrid() {
        return grid;
    }

    public Area getArea(int colLocation, int rowLocation) {
        // Check column
        if (colLocation < 0 || colLocation > MAX_COL) {
            throw new IllegalArgumentException("Column location must be >= 0 and <= " + MAX_COL);
        }
        // Check Row
        if (rowLocation < 0 || rowLocation > MAX_ROW) {
            throw new IllegalArgumentException("Row location must be >= 0 and <= " + MAX_ROW);
        }
        return grid[colLocation][rowLocation];
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public static boolean hasInstance() {
        return !(instance == null);
    }

    public Area getCurrentArea() {
        return getArea(player.getColLocation(), player.getRowLocation());
    }

    public String getCoordinateText() {
        String coordinate = "[";
        coordinate += player.getColLocation() + ",";
        coordinate += player.getRowLocation() + "]";
        return coordinate;
    }

    /* Mutators */
    public void setArea(int colLocation, int rowLocation, Area inArea) {
        // Check column
        if (colLocation < 0 || colLocation > MAX_COL) {
            throw new IllegalArgumentException("Column location must be >= 0 and <= " + MAX_COL);
        }
        // Check Row
        if (rowLocation < 0 || rowLocation > MAX_ROW) {
            throw new IllegalArgumentException("Row location must be >= 0 and <= " + MAX_ROW);
        }
        grid[colLocation][rowLocation] = inArea;
    }

    public void setCurrentArea(Area inArea) {
        setArea(player.getColLocation(), player.getRowLocation(), inArea);
    }

    public void setPlayer(Player inPlayer) {
        player = inPlayer;
    }

    void setGameOver() {
        gameOver = true;
    }

    public static void newGame() {
        instance = new GameData();
    }

    /* Functions */
    public void generateMap() {
        for (int yy = 0; yy <= MAX_ROW; yy++) {
            // Iterate over rows
            for (int xx = 0; xx <= MAX_COL; xx++) {
                // Iterate through columns
                grid[yy][xx] = new Area();
                grid[yy][xx].randomize();
            }
        }
    }

    public void actionItems(int type, LinkedList<Item> selectedItems) {

        // TODO: TIDY THIS SHIT UP

        Equipment currEquipment;
        Food currFood;
        double itemMass = 0.0;
        double foodHealth = 0.0;
        int itemValue = 0;

        // Always
        for (Item currItem : selectedItems) {
            // Sum Mass
            if (currItem instanceof Equipment) {
                currEquipment = (Equipment) currItem;
                itemMass += currEquipment.getMass();
            }

            // Reset selected
            currItem.setSelected(false);
        }

        // If Buying or selling
        if (type == ListFragment.MARKET_SELL || type == ListFragment.MARKET_BUY) {
            // Sum value
            for (Item currItem : selectedItems) {
                itemValue += currItem.getValue();
            }
        }

        switch (type) {
            case ListFragment.MARKET_SELL:
                // Change player cash
                itemValue = (int) Math.round(itemValue * SELL_MARKDOWN);
                player.setCash(player.getCash() + itemValue);
                // No break: will do WILDERNESS_DROP actions

            case ListFragment.WILDERNESS_DROP:
                // Move Items to area
                getPlayer().getItemList().removeAll(selectedItems);
                getCurrentArea().getItemList().addAll(selectedItems);

                // Change player mass
                player.setEquipmentMass(player.getEquipmentMass() - itemMass);
                break;

            case ListFragment.MARKET_BUY:
                // Change player cash
                try {
                    player.setCash(player.getCash() - itemValue);
                }
                catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Not enough money to buy the selected items.");
                }
                // No break: will do WILDERNESS_PICK actions

            case ListFragment.WILDERNESS_PICK:
                // Move items to player
                getCurrentArea().getItemList().removeAll(selectedItems);
                getPlayer().getItemList().addAll(selectedItems);

                // Change player mass
                player.setEquipmentMass(player.getEquipmentMass() + itemMass);
                break;

            case ListFragment.BACKPACK_FOOD:
                // Eat food
                for (Item currItem : selectedItems) {
                    if (currItem instanceof Food) {
                        currFood = (Food) currItem;
                        foodHealth += currFood.getHealth();
                    }
                    else {
                        throw new IllegalStateException("Cannot eat non-food item");
                    }
                }
                player.setHealth(player.getHealth() + foodHealth);

                // Remove Items from Inventory
                player.getItemList().removeAll(selectedItems);
                player.setEquipmentMass(player.getEquipmentMass() - itemMass);
                break;

            case ListFragment.BACKPACK_EQUIPMENT:
                break;

            default:
                throw new IllegalArgumentException("Unknown action type");
        }
    }
}
