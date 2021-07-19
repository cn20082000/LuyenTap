package com.example.luyentap.Adapters;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.luyentap.Fragments.TrainingFragment;
import com.example.luyentap.Helpers.TblLogHelper;
import com.example.luyentap.Models.Log;
import com.example.luyentap.R;

import org.jetbrains.annotations.NotNull;

public class HomeTabAdapter extends FragmentStateAdapter {

    private final TblLogHelper helper;
    private final Log log;
    private final String[] part;
    private final int[] timeStep;

    public HomeTabAdapter(@NonNull @NotNull Fragment fragment) {
        super(fragment);

        helper = new TblLogHelper(fragment.getContext());
        part = fragment.getResources().getStringArray(R.array.part);
        timeStep = fragment.getResources().getIntArray(R.array.time_step);
        log = helper.getLastLog();
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putStringArray("part", part);
        args.putParcelable("log", log);
        args.putInt("pos", position);
        args.putInt("now", getNow());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return part.length;
    }

    public int getNow() {
        Calendar current = Calendar.getInstance();
        Calendar init = (Calendar) log.getCalendar().clone();
//        android.util.Log.e("hour-now", String.valueOf(current.get(Calendar.HOUR_OF_DAY)));

        for (int i = 0; i < timeStep.length; ++i) {
            Calendar temp = (Calendar) init.clone();
            temp.add(Calendar.HOUR, timeStep[i]);

//            android.util.Log.e("hour", String.valueOf(temp.get(Calendar.HOUR_OF_DAY)));

            if (temp.after(current)) {
                return i;
            }
        }

        return 0;
    }
}
