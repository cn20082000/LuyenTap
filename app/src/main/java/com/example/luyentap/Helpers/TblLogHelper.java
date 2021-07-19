package com.example.luyentap.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;

import com.example.luyentap.Models.Log;
import com.example.luyentap.R;

public class TblLogHelper {
    private SQLiteDatabase db;
    private final SQLHelper helper;
    private final Context context;

    private final String[] allCollumn = {SQLHelper.COL_ID_LOG, SQLHelper.COL_DAY_LOG, SQLHelper.COL_SCORE_LOG,
            SQLHelper.COL_MORNING_LOG, SQLHelper.COL_NOON_LOG, SQLHelper.COL_AFTERNOON_LOG,
            SQLHelper.COL_EVENING_LOG, SQLHelper.COL_NIGHT_LOG};

    public TblLogHelper(Context context) {
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

    public void addLog(long lastLog) {
        long rowCount = getRowCount();

        write();

        Calendar calendar = Calendar.getInstance();

        if (rowCount <= 0) {
            calendar = getNewDay(calendar);

            ContentValues values = newLog(calendar.getTimeInMillis());
            long insertId = db.insert(SQLHelper.TBL_LOG, null, values);
        } else {
            calendar.setTimeInMillis(lastLog);

            Calendar now = Calendar.getInstance();
            while (true) {
                calendar.add(Calendar.DATE, 1);

                if (getNewDay(calendar).after(now)) {
                    break;
                }

                ContentValues values = newLog(getNewDay(calendar).getTimeInMillis());
                long insertId = db.insert(SQLHelper.TBL_LOG, null, values);
            }
        }

        close();
    }

    public Log getLastLog() {
        read();

        Log result = null;
        Cursor cursor = db.query(SQLHelper.TBL_LOG, allCollumn, SQLHelper.COL_ID_LOG
                + " = (SELECT MAX(" + SQLHelper.COL_ID_LOG + ") FROM " + SQLHelper.TBL_LOG + ")",
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result = cursorToLog(cursor);
            cursor.moveToNext();
        }
        cursor.close();

        close();
        return result;
    }

    private Calendar getNewDay(Calendar calendar) {
        int resetTime = context.getResources().getIntArray(R.array.time_step)[0];
        resetTime *= 3600000;

        if (calendar.get(Calendar.MILLISECONDS_IN_DAY) < resetTime) {
            calendar.add(Calendar.DATE, -1);
        }
        calendar.set(Calendar.MILLISECONDS_IN_DAY, 0);
        return calendar;
    }

    private ContentValues newLog(long timeInMillis) {
        ContentValues values = new ContentValues();

        values.put(SQLHelper.COL_DAY_LOG, timeInMillis);
        values.put(SQLHelper.COL_SCORE_LOG, 0);
        values.put(SQLHelper.COL_MORNING_LOG, 0);
        values.put(SQLHelper.COL_NOON_LOG, 0);
        values.put(SQLHelper.COL_AFTERNOON_LOG, 0);
        values.put(SQLHelper.COL_EVENING_LOG, 0);
        values.put(SQLHelper.COL_NIGHT_LOG, 0);

        return values;
    }

    private long getRowCount() {
        read();

        long result = DatabaseUtils.queryNumEntries(db, SQLHelper.TBL_LOG);

        close();
        return result;
    }

    private Log cursorToLog(Cursor cursor) {
        Log log = new Log();
        log.setId(cursor.getInt(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(1));
        log.setCalendar(calendar);
        log.setAddScore(cursor.getInt(2));
        log.setMorning(cursor.getInt(3) != 0);
        log.setNoon(cursor.getInt(4) != 0);
        log.setAfternoon(cursor.getInt(5) != 0);
        log.setEvening(cursor.getInt(6) != 0);
        log.setNight(cursor.getInt(7) != 0);

        return log;
    }
}
