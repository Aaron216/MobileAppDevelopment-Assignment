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

public class GameData {
    /* Constants */
    public static final int MAX_ROW = 10;
    public static final int MAX_COL = 10;

    /* Fields */
    private Area[][] grid;
    private Player player;
    private static GameData instance;

    /* Constructor */
    private GameData() {
        grid = new Area[MAX_ROW+1][MAX_COL+1];
        player = new Player();
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

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
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

    public void setPlayer(Player inPlayer) {
        player = inPlayer;
    }

    /* Functions */
    public void generateMap() {
        for (int yy = 0; yy < MAX_ROW; yy++) {
            // Iterate over rows
            for (int xx = 0; xx < MAX_COL; xx++) {
                // Iterate through columns
                grid[yy][xx] = new Area();
                grid[yy][xx].randomize();
            }
        }
    }
}
