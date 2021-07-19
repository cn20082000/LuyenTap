package com.example.luyentap.Models;

import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;

public class Log implements Parcelable {
    private int id;
    private Calendar calendar;
    private int addScore;
    private boolean morning;
    private boolean noon;
    private boolean afternoon;
    private boolean evening;
    private boolean night;

    public Log() {}

    public Log(int id, Calendar calendar, int addScore, boolean morning, boolean noon,
               boolean afternoon, boolean evening, boolean night) {
        this.id = id;
        this.calendar = calendar;
        this.addScore = addScore;
        this.morning = morning;
        this.noon = noon;
        this.afternoon = afternoon;
        this.evening = evening;
        this.night = night;
    }

    protected Log(Parcel in) {
        id = in.readInt();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(in.readLong());
        addScore = in.readInt();
        morning = in.readByte() != 0;
        noon = in.readByte() != 0;
        afternoon = in.readByte() != 0;
        evening = in.readByte() != 0;
        night = in.readByte() != 0;
    }

    public static final Creator<Log> CREATOR = new Creator<Log>() {
        @Override
        public Log createFromParcel(Parcel in) {
            return new Log(in);
        }

        @Override
        public Log[] newArray(int size) {
            return new Log[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getAddScore() {
        return addScore;
    }

    public void setAddScore(int addScore) {
        this.addScore = addScore;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isNoon() {
        return noon;
    }

    public void setNoon(boolean noon) {
        this.noon = noon;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(calendar.getTimeInMillis());
        dest.writeInt(addScore);
        dest.writeByte((byte) (morning ? 1 : 0));
        dest.writeByte((byte) (noon ? 1 : 0));
        dest.writeByte((byte) (afternoon ? 1 : 0));
        dest.writeByte((byte) (evening ? 1 : 0));
        dest.writeByte((byte) (night ? 1 : 0));
    }
}
