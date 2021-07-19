package com.example.luyentap.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String TBL_EX = "Exercise";
    public static final String COL_ID_EX = "id";
    public static final String COL_NAME_EX = "name";
    public static final String COL_ITEM_EX = "itemPerTime";
    public static final String COL_SCORE_EX = "scorePerItem";

    public static final String TBL_LOG = "Log";
    public static final String COL_ID_LOG = "id";
    public static final String COL_DAY_LOG = "day";
    public static final String COL_SCORE_LOG = "addScore";
    public static final String COL_MORNING_LOG = "morning";
    public static final String COL_NOON_LOG = "noon";
    public static final String COL_AFTERNOON_LOG = "afternoon";
    public static final String COL_EVENING_LOG = "evening";
    public static final String COL_NIGHT_LOG = "night";

    public static final String DATABASE_NAME = "luyentap.db";
    public static final int DATABASE_VERSION = 1;

    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTblEx = "CREATE TABLE " + TBL_EX + " ("
                + COL_ID_EX + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME_EX + " TEXT NOT NULL, "
                + COL_ITEM_EX + " INTEGER NOT NULL, "
                + COL_SCORE_EX + " INTEGER NOT NULL);";
        String createTblLog = "CREATE TABLE " + TBL_LOG + " ("
                + COL_ID_LOG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DAY_LOG + " INTEGER NOT NULL, "
                + COL_SCORE_LOG + " INTEGER NOT NULL, "
                + COL_MORNING_LOG + " INTEGER NOT NULL, "
                + COL_NOON_LOG + " INTEGER NOT NULL, "
                + COL_AFTERNOON_LOG + " INTEGER NOT NULL, "
                + COL_EVENING_LOG + " INTEGER NOT NULL, "
                + COL_NIGHT_LOG + " INTEGER NOT NULL);";

        db.execSQL(createTblEx);
        db.execSQL(createTblLog);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_EX);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_LOG);
        onCreate(db);
    }

    public void deleteDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TBL_EX);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_LOG);

        onCreate(db);
        db.close();
    }
}
