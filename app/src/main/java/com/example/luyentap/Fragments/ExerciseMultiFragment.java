package com.example.luyentap.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luyentap.Adapters.ExerciseMultiListAdapter;
import com.example.luyentap.R;

import org.jetbrains.annotations.NotNull;

public class ExerciseMultiFragment extends Fragment {

    private RecyclerView rv;

    private String[] part;
    private final String[] time = {"3:00 - 11:00", "11:00 - 15:00",
            "15:00 - 20:00", "20:00 - 23:00", "23:00 - 3:00"};

    public ExerciseMultiFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_multi, container, false);

        rv = view.findViewById(R.id.rv_exercise_multi);

        part = view.getResources().getStringArray(R.array.part);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(new ExerciseMultiListAdapter(getContext(), part, time));

        return view;
    }
}
