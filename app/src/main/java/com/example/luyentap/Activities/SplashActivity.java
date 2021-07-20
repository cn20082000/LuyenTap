package com.example.luyentap.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.luyentap.Helpers.SQLHelper;
import com.example.luyentap.Helpers.TblLogHelper;
import com.example.luyentap.R;

import java.util.HashSet;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TblLogHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences(getString(R.string.app_shared_pref), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        helper = new TblLogHelper(this);

        // TODO: khoi tao lai
//        initSharePreferences();
//        new SQLHelper(this).deleteDatabase();

        if (!sharedPreferences.contains("last-log")) {
            initSharePreferences();
        }

        long lastLog = sharedPreferences.getLong("last-log", 0);
        helper.addLog(lastLog);

        update();

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initSharePreferences() {
        editor.putInt("score", 0);

        editor.putStringSet("morning-ex", new HashSet<>());
        editor.putStringSet("noon-ex", new HashSet<>());
        editor.putStringSet("afternoon-ex", new HashSet<>());
        editor.putStringSet("evening-ex", new HashSet<>());
        editor.putStringSet("night-ex", new HashSet<>());

        editor.putLong("last-log", Calendar.getInstance().getTimeInMillis());

        editor.apply();
    }

    private void update() {
        editor.putLong("last-log", Calendar.getInstance().getTimeInMillis());

        editor.apply();
    }
}