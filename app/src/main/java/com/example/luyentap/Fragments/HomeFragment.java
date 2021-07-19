package com.example.luyentap.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.luyentap.Adapters.HomeTabAdapter;
import com.example.luyentap.Helpers.TblLogHelper;
import com.example.luyentap.Models.Log;
import com.example.luyentap.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private HomeTabAdapter tabAdapter;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private String[] part;

    public HomeFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabAdapter = new HomeTabAdapter(this);

        viewPager = view.findViewById(R.id.vp_home);
        tabLayout = view.findViewById(R.id.tl_home);

        part = view.getResources().getStringArray(R.array.part);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(tabAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, pos) -> tab.setText(part[pos])).attach();
    }
}
