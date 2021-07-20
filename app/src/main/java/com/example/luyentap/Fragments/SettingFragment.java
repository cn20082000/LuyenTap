package com.example.luyentap.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.luyentap.Helpers.SQLHelper;
import com.example.luyentap.Helpers.TblLogHelper;
import com.example.luyentap.R;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class SettingFragment extends Fragment {

    private MaterialButton btnDev;

    public SettingFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        btnDev = view.findViewById(R.id.btn_dev_setting);

        btnDev.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setTitle(R.string.btn_dev);
            alert.setMessage("Sau khi xóa sẽ thoát chương trình.");
            alert.setPositiveButton(R.string.btn_accept, (dialog, which) -> {
                SharedPreferences sharedPreferences =
                        view.getContext().getSharedPreferences(getString(R.string.app_shared_pref),
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("score", 0);

                editor.putStringSet("morning-ex", new HashSet<>());
                editor.putStringSet("noon-ex", new HashSet<>());
                editor.putStringSet("afternoon-ex", new HashSet<>());
                editor.putStringSet("evening-ex", new HashSet<>());
                editor.putStringSet("night-ex", new HashSet<>());

                editor.putLong("last-log", Calendar.getInstance().getTimeInMillis());

                editor.apply();

                new SQLHelper(view.getContext()).deleteDatabase();
                System.exit(0);
            });

            alert.setNegativeButton(R.string.btn_cancel, (dialog, which) -> { });

            alert.show();
        });

        return view;
    }
}
