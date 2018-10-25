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
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import au.edu.curtin.madassignment.Database.*;
import au.edu.curtin.madassignment.Database.GameSchema.*;
import au.edu.curtin.madassignment.Fragments.ListFragment;

public class GameData {
    /* Constants */
    static final double SELL_MARKDOWN = 0.75;
    public static final int MAX_ROW = 11;
    public static final int MAX_COL = 11;

    private static final int GAME_NEW = 0;
    private static final int GAME_IN_PROGRESS = 1;
    private static final int GAME_WON = 2;
    private static final int GAME_OVER = 3;

    /* Fields */
    private SQLiteDatabase db;
    private Area[][] grid;
    private Player player;
    private int gameState;
    private static GameData instance;

    /* Constructor */
    private GameData() {
        this.grid = new Area[MAX_ROW][MAX_COL];
        this.player = new Player();
        this.gameState = GAME_NEW;
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
            throw new IllegalArgumentException("Row location must be >= 0 and < " + MAX_ROW);
        }
        // Check column
        if (col < 0 || col >= MAX_COL) {
            throw new IllegalArgumentException("Column location must be >= 0 and < " + MAX_COL);
        }

        return grid[row][col];
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isGameNew() {
        return (gameState == GAME_NEW);
    }

    public boolean isGameInProgress() {
        return (gameState == GAME_IN_PROGRESS);
    }

    public boolean isGameOver() {
        return (gameState == GAME_OVER);
    }

    public boolean isGameWon() {
        return (gameState == GAME_WON);
    }

    public boolean isGameEnd() {
        return (gameState > GAME_IN_PROGRESS);
    }

    public static GameData getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Game data instance has not been created");
        }
        return instance;
    }

    public static GameData getInstance(Context context) {
        if (instance == null) {
            instance = new GameData(context);
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
    private void setArea(int row, int col, Area inArea) {
        // Check Row
        if (row < 0 || row >= MAX_ROW) {
            throw new IllegalArgumentException("Row location must be >= 0 and <= " + MAX_ROW);
        }
        // Check column
        if (col < 0 || col >= MAX_COL) {
            throw new IllegalArgumentException("Column location must be >= 0 and <= " + MAX_COL);
        }

        grid[row][col] = inArea;
    }

    public void setCurrentArea(Area inArea) {
        setArea(player.getRowLocation(), player.getColLocation(), inArea);
    }

    private void setPlayer(Player inPlayer, boolean updateDatabase) {
        if (inPlayer == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        this.player = inPlayer;

        if (updateDatabase) {
            dbAddPlayer();
        }
    }

    private void setGameInProgress() {
        gameState = GAME_IN_PROGRESS;
    }

    void setGameOver() {
        gameState = GAME_OVER;
        dbDeleteAll();
    }

    void setGameWon() {
        gameState = GAME_WON;
        dbDeleteAll();
    }

    public void newGame() {
        setGameInProgress();
        Player newPlayer = new Player();
        newPlayer.setRandomLocation();
        setPlayer(newPlayer, true);
        generateMap();
        dbNewGame();
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
    public void dbLoadGame() {
        dbReadAreaGrid();
        dbReadPlayer();
        setGameInProgress();
    }

    private void dbNewGame() {
        dbAddAreaGrid();
        dbAddPlayer();
    }

    private void dbDeleteAll() {
        db.execSQL("delete from " + PlayerTable.NAME);
        db.execSQL("delete from " + PlayerItemTable.NAME);
        db.execSQL("delete from " + AreaTable.NAME);
        db.execSQL("delete from " + AreaItemTable.NAME);
    }

    /* Area Table */
    private void dbReadAreaGrid() {
        Area currArea;
        int row;
        int col;

        AreaCursor cursor = new AreaCursor(
                db.query(AreaTable.NAME, null, null, null, null, null, null));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // Get area
                currArea = cursor.getArea();

                // Get Area Items
                row = currArea.getRowLocation();
                col = currArea.getColLocation();
                currArea.setItemList(dbReadAreaItems(row, col), false);

                // Put in array
                setArea(row, col, currArea);

                // Move cursor
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
    }

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
    private List<Item> dbReadAreaItems(int row, int col) {
        List<Item> items = new LinkedList<>();

        String whereClause = "";
        whereClause += AreaItemTable.Cols.ROW_LOCATION + " = ? AND ";
        whereClause += AreaItemTable.Cols.COL_LOCATION + " = ?";
        String[] whereArgs = { String.valueOf(row), String.valueOf(col) };

        ItemCursor cursor = new ItemCursor(
                db.query(AreaItemTable.NAME, null, whereClause, whereArgs, null, null, null));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getAreaItem());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return items;
    }

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
    public boolean dbCheckForPlayer() {
        long numPlayers = DatabaseUtils.queryNumEntries(db, PlayerTable.NAME);
        return (numPlayers > 0);
    }

    private void dbReadPlayer() {
        Player currPlayer;

        PlayerCursor cursor = new PlayerCursor(
                db.query(PlayerTable.NAME, null, null, null, null, null, null));
        try {
            cursor.moveToFirst();
            currPlayer = cursor.getPlayer();
            currPlayer.setItemList(dbReadPlayerItems(), false);
            setPlayer(currPlayer, false);
        }
        finally {
            cursor.close();
        }
    }

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
    private List<Item> dbReadPlayerItems() {
        List<Item> items = new LinkedList<>();

        ItemCursor cursor = new ItemCursor(
                db.query(PlayerItemTable.NAME, null, null, null, null, null, null));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getPlayerItem());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return items;
    }

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
