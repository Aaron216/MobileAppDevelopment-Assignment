package au.edu.curtin.madassignment.Model;

import au.edu.curtin.madassignment.Model.GameSchema.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDbHelper extends SQLiteOpenHelper {
    /* Constants */
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "explorergame.db";

    /* Constructor */
    public GameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /* Overrides */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPlayerTableQuery());
        db.execSQL(createPlayerItemTableQuery());
        db.execSQL(createAreaTableQuery());
        db.execSQL(createAreaItemTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {

    }

    /* Private Functions */
    private String createPlayerTableQuery() {
        String sqlQuery = "";

        sqlQuery += "create table " + PlayerTable.NAME + "(";
        sqlQuery += " _id integer primary key autoincrement, ";
        sqlQuery += PlayerTable.Cols.ID + ", ";
        sqlQuery += PlayerTable.Cols.ROW_LOCATION + ", ";
        sqlQuery += PlayerTable.Cols.COL_LOCATION + ", ";
        sqlQuery += PlayerTable.Cols.CASH + ", ";
        sqlQuery += PlayerTable.Cols.HEALTH + ", ";
        sqlQuery += PlayerTable.Cols.EQUIPMENT_MASS + ", ";
        sqlQuery += PlayerTable.Cols.HAS_JADE_MONKEY + ", ";
        sqlQuery += PlayerTable.Cols.HAS_ROADMAP + ", ";
        sqlQuery += PlayerTable.Cols.HAS_ICE_SCRAPER + ")";

        return  sqlQuery;
    }

    private String createPlayerItemTableQuery() {
        String sqlQuery = "";

        sqlQuery += "create table " + PlayerItemTable.NAME + "(";
        sqlQuery += " _id integer primary key autoincrement, ";
        sqlQuery += PlayerItemTable.Cols.ID + ", ";
        sqlQuery += PlayerItemTable.Cols.TYPE + ", ";
        sqlQuery += PlayerItemTable.Cols.DESCRIPTION + ", ";
        sqlQuery += PlayerItemTable.Cols.VALUE + ", ";
        sqlQuery += PlayerItemTable.Cols.MASS + ", ";
        sqlQuery += PlayerItemTable.Cols.HEALTH + ")";

        return sqlQuery;
    }

    private String createAreaTableQuery() {
        String sqlQuery = "";

        sqlQuery += "create table " + AreaTable.NAME + "(";
        sqlQuery += " _id integer primary key autoincrement, ";
        sqlQuery += AreaTable.Cols.ID + ", ";
        sqlQuery += AreaTable.Cols.ROW_LOCATION + ", ";
        sqlQuery += AreaTable.Cols.COL_LOCATION + ", ";
        sqlQuery += AreaTable.Cols.IS_TOWN + ", ";
        sqlQuery += AreaTable.Cols.DESCRIPTION + ", ";
        sqlQuery += AreaTable.Cols.IS_STARRED + ", ";
        sqlQuery += AreaTable.Cols.IS_EXPLORED + ")";

        return sqlQuery;
    }

    private String createAreaItemTableQuery() {
        String sqlQuery = "";

        sqlQuery += "create table " + AreaItemTable.NAME + "(";
        sqlQuery += " _id integer primary key autoincrement, ";
        sqlQuery += AreaItemTable.Cols.ID + ", ";
        sqlQuery += AreaItemTable.Cols.ROW_LOCATION + ", ";
        sqlQuery += AreaItemTable.Cols.COL_LOCATION + ", ";
        sqlQuery += AreaItemTable.Cols.TYPE + ", ";
        sqlQuery += AreaItemTable.Cols.DESCRIPTION + ", ";
        sqlQuery += AreaItemTable.Cols.VALUE + ", ";
        sqlQuery += AreaItemTable.Cols.MASS + ", ";
        sqlQuery += AreaItemTable.Cols.HEALTH + ")";

        return sqlQuery;
    }
}
