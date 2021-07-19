package com.example.luyentap.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.luyentap.Adapters.ExerciseTabAdapter;
import com.example.luyentap.Adapters.HomeTabAdapter;
import com.example.luyentap.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class ExerciseFragment extends Fragment {

    private ExerciseTabAdapter tabAdapter;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private final String[] TAB_NAME = {"Bài tập kết hợp", "Bài tập đơn"};

    public ExerciseFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        tabAdapter = new ExerciseTabAdapter(this);

        viewPager = view.findViewById(R.id.vp_exercise);
        tabLayout = view.findViewById(R.id.tl_exercise);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(tabAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, pos) -> tab.setText(TAB_NAME[pos])).attach();
    }
}
