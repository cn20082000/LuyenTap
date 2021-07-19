package com.example.luyentap.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luyentap.Adapters.ExerciseSingleListAdapter;
import com.example.luyentap.Helpers.TblExHelper;
import com.example.luyentap.Models.Ex;
import com.example.luyentap.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class ExerciseSingleFragment extends Fragment {

    private TblExHelper helper;
    private ExerciseSingleListAdapter adapter;

    private RecyclerView rv;
    private FloatingActionButton btnAdd;
    private View dialogAddEx;
    private TextInputLayout edtNameDialog, edtItemDialog;

    public ExerciseSingleFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_single, container, false);

        helper = new TblExHelper(view.getContext());

        rv = view.findViewById(R.id.rv_exercise_single);
        btnAdd = view.findViewById(R.id.btn_add_exercise_single);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        adapter = new ExerciseSingleListAdapter(getContext());
        rv.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddEx = inflater.inflate(R.layout.dialog_add_ex,
                        (ViewGroup) view, false);
                edtNameDialog = dialogAddEx.findViewById(R.id.edt_name_add_ex);
                edtItemDialog = dialogAddEx.findViewById(R.id.edt_item_add_ex);

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle(R.string.ex_add);
                alert.setView(dialogAddEx);

                alert.setPositiveButton("Đồng ý",
                        (dialog, which) -> {
                            String name = edtNameDialog.getEditText().getText().toString();
                            int item = Integer.parseInt(edtItemDialog.getEditText()
                                    .getText().toString());
                            Ex ex = new Ex(0, name, item, 0);
                            helper.addEx(ex);
                            adapter.updateData();
                        });

                alert.setNegativeButton("Hủy", (dialog, which) -> {

                });

                alert.show();
            }
        });



        return view;
    }
}
