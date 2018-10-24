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
import android.support.v4.app.FragmentManager;

import java.util.List;
import java.util.Random;

import au.edu.curtin.madassignment.Fragments.ListFragment;
import au.edu.curtin.madassignment.Model.GameSchema.*;

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
        generateMap();
        getCurrentArea().setExplored(true);
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

    public void setPlayer(Player inPlayer) {
        player = inPlayer;
    }

    void setGameOver() {
        gameOver = true;
    }

    void setGameWon() {
        gameWon = true;
    }

    public static void newGame() {
        instance = new GameData();
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

        dbAddAreaGrid();
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

    /* Private Functions */
    private void dbAddAreaGrid() {
        db.execSQL("delete from " + AreaTable.NAME);
        db.execSQL("delete from " + AreaItemTable.NAME);

        // Iterate over rows and cols
        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {
                dBAddArea(grid[row][col]);

                // Add Items to database
                for (Item currItem : grid[row][col].getItemList()) {
                    dbAddAreaItem(row, col, currItem);
                }
            }
        }
    }

    private void dBAddArea(Area area) {
        ContentValues cv = new ContentValues();

        cv.put(AreaTable.Cols.ROW_LOCATION, area.getRowLocation());
        cv.put(AreaTable.Cols.COL_LOCATION, area.getColLocation());
        cv.put(AreaTable.Cols.IS_TOWN, area.isTown());
        cv.put(AreaTable.Cols.DESCRIPTION, area.getDescription());
        cv.put(AreaTable.Cols.IS_STARRED, area.isStarred());
        cv.put(AreaTable.Cols.IS_EXPLORED, area.isExplored());

        db.insert(AreaTable.NAME, null, cv);
    }

    private void dbAddAreaItem(int row, int col, Item item) {
        ContentValues cv = new ContentValues();

        cv.put(AreaItemTable.Cols.ROW_LOCATION, row);
        cv.put(AreaItemTable.Cols.COL_LOCATION, col);
        cv.put(AreaItemTable.Cols.TYPE, item.getType());
        cv.put(AreaItemTable.Cols.DESCRIPTION, item.getDescription());
        cv.put(AreaItemTable.Cols.VALUE, item.getValue());

        if (item instanceof Equipment) {
            cv.put(AreaItemTable.Cols.MASS, ((Equipment) item).getMass());
        }
        else {
            cv.put(AreaItemTable.Cols.MASS, 0.0);
        }

        if (item instanceof Food) {
            cv.put(AreaItemTable.Cols.HEALTH, ((Food) item).getHealth());
        }
        else {
            cv.put(AreaItemTable.Cols.HEALTH, 0.0);
        }
    }
}
