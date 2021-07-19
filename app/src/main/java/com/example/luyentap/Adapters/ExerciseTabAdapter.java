package com.example.luyentap.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.luyentap.Fragments.ExerciseMultiFragment;
import com.example.luyentap.Fragments.ExerciseSingleFragment;

import org.jetbrains.annotations.NotNull;

public class ExerciseTabAdapter extends FragmentStateAdapter {

    public ExerciseTabAdapter(@NonNull @NotNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            Fragment fragment = new ExerciseMultiFragment();
            return fragment;
        }
        Fragment fragment = new ExerciseSingleFragment();
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
