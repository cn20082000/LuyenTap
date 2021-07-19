package com.example.luyentap.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;

import com.example.luyentap.Models.Ex;
import com.example.luyentap.Models.Log;

import java.util.ArrayList;
import java.util.List;

public class TblExHelper {
    private SQLiteDatabase db;
    private final SQLHelper helper;
    private final Context context;

    private final String[] allCollumn = {SQLHelper.COL_ID_EX, SQLHelper.COL_NAME_EX,
            SQLHelper.COL_ITEM_EX, SQLHelper.COL_SCORE_EX};


    public TblExHelper(Context context) {
        this.context = context;
        helper = new SQLHelper(context);
    }

    private void write() throws SQLException {
        db = helper.getWritableDatabase();
    }

    private void read() throws  SQLException {
        db = helper.getReadableDatabase();
    }

    private void close() {
        db.close();
    }

    public void addEx(Ex ex) {
        write();

        ContentValues values = new ContentValues();
        values.put(SQLHelper.COL_NAME_EX, ex.getName());
        values.put(SQLHelper.COL_ITEM_EX, ex.getItem());
        values.put(SQLHelper.COL_SCORE_EX, ex.getScore());

        long insertId = db.insert(SQLHelper.TBL_EX, null, values);

        close();
    }

    public void updateEx(Ex ex) {
        write();

        ContentValues values = new ContentValues();
        values.put(SQLHelper.COL_NAME_EX, ex.getName());
        values.put(SQLHelper.COL_ITEM_EX, ex.getItem());
        values.put(SQLHelper.COL_SCORE_EX, ex.getScore());

        db.update(SQLHelper.TBL_EX, values, SQLHelper.COL_ID_EX + " = ?",
                new String[] {String.valueOf(ex.getId())});

        close();
    }

    public void deleteEx(Ex ex) {
        write();

        db.delete(SQLHelper.TBL_EX, SQLHelper.COL_ID_EX + " = ?",
                new String[] {String.valueOf(ex.getId())});

        close();
    }

    public List<Ex> getAllEx() {
        List<Ex> result = new ArrayList<>();
        read();

        Cursor cursor = db.query(SQLHelper.TBL_EX, allCollumn, null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(cursorToEx(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        close();
        return result;
    }

    public Ex cursorToEx(Cursor cursor) {
        Ex ex = new Ex();
        ex.setId(cursor.getInt(0));
        ex.setName(cursor.getString(1));
        ex.setItem(cursor.getInt(2));
        ex.setScore(cursor.getInt(3));

        return ex;
    }
}
