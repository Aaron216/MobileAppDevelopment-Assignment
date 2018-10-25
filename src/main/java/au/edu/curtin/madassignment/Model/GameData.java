/*
  Curtin University
  Mobile Application Development
  Assignment
  Aaron Musgrave
  25/10/2018

  Game Data Class
  Responsible for storing data about the current game
 */

package au.edu.curtin.madassignment.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Random;

import au.edu.curtin.madassignment.Database.GameDbHelper;
import au.edu.curtin.madassignment.Fragments.ListFragment;
import au.edu.curtin.madassignment.Database.GameSchema.*;

public class GameData {
    /* Constants */
    static final double SELL_MARKDOWN = 0.75;
    public static final int MAX_ROW = 11;
    public static final int MAX_COL = 11;

    /* Fields */
    private SQLiteDatabase db;
    private Area[][] grid;
    private Player player;
    private boolean gameOver;
    private boolean gameWon;
    private static GameData instance;

    /* Constructor */
    private GameData() {
        this.grid = new Area[MAX_ROW][MAX_COL];
        this.player = new Player();
        this.gameOver = false;
        this.gameWon = false;
    }

    private GameData(Context context) {
        this();
        this.db = new GameDbHelper(context.getApplicationContext()).getWritableDatabase();
    }

    /* Accessors */
    public Area[][] getGrid() {
        return grid;
    }

    public Area getArea(int row, int col) {
        // Check Row
        if (row < 0 || row >= MAX_ROW) {
            throw new IllegalArgumentException("Row location must be >= 0 and <= " + MAX_ROW);
        }
        // Check column
        if (col < 0 || col >= MAX_COL) {
            throw new IllegalArgumentException("Column location must be >= 0 and <= " + MAX_COL);
        }

        return grid[row][col];
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameEnd() {
        return (gameOver || gameWon);
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
        return getArea(player.getRowLocation(), player.getColLocation());
    }

    /* Mutators */
    private void setArea(int colLocation, int rowLocation, Area inArea) {
        // Check column
        if (colLocation < 0 || colLocation >= MAX_COL) {
            throw new IllegalArgumentException("Column location must be >= 0 and <= " + MAX_COL);
        }
        // Check Row
        if (rowLocation < 0 || rowLocation >= MAX_ROW) {
            throw new IllegalArgumentException("Row location must be >= 0 and <= " + MAX_ROW);
        }
        grid[colLocation][rowLocation] = inArea;
    }

    public void setCurrentArea(Area inArea) {
        setArea(player.getColLocation(), player.getRowLocation(), inArea);
    }

    private void setPlayer(Player inPlayer) {
        if (inPlayer == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = inPlayer;
        dbAddPlayer();
    }

    void setGameOver() {
        gameOver = true;
    }

    void setGameWon() {
        gameWon = true;
    }

    public static void newGame(Context context) {
        instance = new GameData(context);
        instance.generateMap();
        instance.dbNewGame();
    }

    /* Functions */
    public void generateMap() {
        Area currArea;

        // Randomly Generate Areas
        for (int row = 0; row < MAX_ROW; row++) {
            // Iterate over rows
            for (int col = 0; col < MAX_COL; col++) {
                // Iterate through columns
                currArea = new Area();

                currArea.setRowLocation(row);
                currArea.setColLocation(col);
                currArea.randomize();

                grid[row][col] = currArea;
            }
        }

        // Create special items
        Random random = new Random();
        Item[] specialItems = new Item[Equipment.SPECIAL_NAMES.length];

        for (int ii = 0; ii < Equipment.SPECIAL_NAMES.length; ii++) {
            specialItems[ii] = new Equipment(Equipment.SPECIAL_NAMES[ii]);
            getArea(random.nextInt(MAX_ROW), random.nextInt(MAX_COL)).addItem(specialItems[ii]);
        }

        // Set current player area as explored
        getCurrentArea().setExplored();
    }

    public void actionItems(Context context, int type, List<Item> selectedItems) {
        switch (type) {
            case ListFragment.MARKET_SELL:
                player.sellItems(selectedItems);
                getCurrentArea().addItems(selectedItems);
                break;

            case ListFragment.MARKET_BUY:
                player.buyItems(selectedItems);
                getCurrentArea().removeItems(selectedItems);

            case ListFragment.WILDERNESS_DROP:
                player.removeItems(selectedItems);
                getCurrentArea().addItems(selectedItems);
                break;

            case ListFragment.WILDERNESS_PICK:
                player.addItems(selectedItems);
                getCurrentArea().removeItems(selectedItems);
                break;

            case ListFragment.BACKPACK_FOOD:
                player.eatItems(selectedItems);
                break;

            case ListFragment.BACKPACK_EQUIPMENT:
                player.useItems(context, selectedItems);
                break;

            default:
                throw new IllegalArgumentException("Unknown action type");
        }
    }

    /**
     * DATABASE FUNCTIONS
     */
    private void dbNewGame() {
        dbAddAreaGrid();
        dbAddPlayer();
    }

    /* Area Table */
    private void dbAddAreaGrid() {
        // Refresh Table
        db.execSQL("delete from " + AreaTable.NAME);
        db.execSQL("delete from " + AreaItemTable.NAME);

        // Iterate over rows and cols
        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {
                dBAddArea(grid[row][col]);
                dbAddAreaItems(row, col, grid[row][col].getItemList());
            }
        }
    }

    private void dBAddArea(Area area) {
        db.insert(AreaTable.NAME, null, area.getContentValues());
    }

    void dbUpdateArea(Area area) {
        ContentValues cv = area.getContentValues();
        String[] whereValue = { area.getIDString() };
        db.update(AreaTable.NAME, cv, AreaTable.Cols.ID + " = ?", whereValue);
    }

    /* Area Item Table */
    void dbAddAreaItems(int row, int col, List<Item> items) {
        for (Item currItem : items) {
            dbAddAreaItem(row, col, currItem);
        }
    }

    void dbAddAreaItem(int row, int col, Item item) {
        db.insert(AreaItemTable.NAME, null, item.getAreaItemContentValues(row, col));
    }

    void dbRemoveAreaItems(List<Item> items) {
        for (Item currItem : items) {
            dbRemoveAreaItem(currItem);
        }
    }

    private void dbRemoveAreaItem(Item item) {
        String[] whereValue = { item.getIDString() };
        db.delete(AreaItemTable.NAME, AreaItemTable.Cols.ID + " = ?", whereValue);
    }

    void dbReplaceAreaItems(int row, int col, List<Item> items) {
        dbRemoveAreaItems(items);
        dbAddAreaItems(row, col, items);
    }

    /* Player Table */
    private void dbAddPlayer() {
        // Empty Tables: Only 1 player
        db.execSQL("delete from " + PlayerTable.NAME);
        db.execSQL("delete from " + PlayerItemTable.NAME);

        db.insert(PlayerTable.NAME, null, player.getContentValues());

        dbAddPlayerItems(player.getItemList());
    }

    void dbUpdatePlayer(Player player) {
        ContentValues cv = player.getContentValues();
        String[] whereValue = { player.getIDString() };
        db.update(PlayerTable.NAME, cv, PlayerTable.Cols.ID + " = ?", whereValue);
    }

    /* Player Item Table */
    void dbAddPlayerItems(List<Item> items) {
        for (Item currItem : items) {
            dbAddPlayerItem(currItem);
        }
    }

    private void dbAddPlayerItem(Item item) {
        db.insert(PlayerItemTable.NAME, null, item.getPlayerItemContentValues());
    }

    void dbRemovePlayerItems(List<Item> items) {
        for (Item currItem : items) {
            dbRemovePlayerItem(currItem);
        }
    }

    void dbRemovePlayerItem(Item item) {
        String[] whereValue = { item.getIDString() };
        db.delete(PlayerItemTable.NAME, PlayerItemTable.Cols.ID + " = ?", whereValue);
    }

    void dbReplacePlayerItems(List<Item> items) {
        db.execSQL("delete from " + PlayerItemTable.NAME);
        dbAddPlayerItems(items);
    }
}
