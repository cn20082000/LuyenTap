package com.example.luyentap.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.luyentap.Helpers.TblExHelper;
import com.example.luyentap.Helpers.TblLogHelper;
import com.example.luyentap.Models.Ex;
import com.example.luyentap.Models.Log;
import com.example.luyentap.R;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

// MARK: Main class
public class TrainingFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private TblExHelper exHelper;
    private TblLogHelper logHelper;

    private TextView tvOpera, tvEx, tvStatus;
    private MaterialButton btnComplete;

    private Log log;
    private String[] part;
    private String[] partEx;
    private int pos;
    private int now;

    public TrainingFragment() {}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        sharedPreferences = view.getContext().getSharedPreferences(getString(R.string.app_shared_pref),
                Context.MODE_PRIVATE);
        exHelper = new TblExHelper(view.getContext());
        logHelper = new TblLogHelper(view.getContext());

        partEx = view.getResources().getStringArray(R.array.part_ex);

        tvOpera = view.findViewById(R.id.tv_opera_train);
        tvEx = view.findViewById(R.id.tv_ex_train);
        tvStatus = view.findViewById(R.id.tv_status_train);
        btnComplete = view.findViewById(R.id.btn_complete_train);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            log = args.getParcelable("log");
            part = args.getStringArray("part");
            pos = args.getInt("pos");
            now = args.getInt("now");
        } else {
            android.util.Log.e("home to training", "bundle empty");
        }

        showStatus();
        showEx();

        btnComplete.setOnLongClickListener(v -> {
            switch (now) {
                case 0: {
                    log.setMorning(true);
                }
                case 1: {
                    log.setNoon(true);
                }
                case 2: {
                    log.setAfternoon(true);
                }
                case 3: {
                    log.setEvening(true);
                }
                case 4: {
                    log.setNight(true);
                }
            }
            logHelper.updateLog(log);
            showStatus();
            return true;
        });
    }

    private void showStatus() {
        if (pos < now) {
            if (getStatus()) {
                tvStatus.setText(R.string.completed_ex);
                tvStatus.setTextColor(ContextCompat.getColor(this.requireContext(),
                        R.color.green_700));
            } else {
                tvStatus.setText(R.string.missing_ex);
                tvStatus.setTextColor(ContextCompat.getColor(this.requireContext(),
                        R.color.red_700));
            }
            btnComplete.setEnabled(false);
        } else if (pos == now) {
            if (getStatus()) {
                tvStatus.setText(R.string.completed_ex);
                tvStatus.setTextColor(ContextCompat.getColor(this.requireContext(),
                        R.color.green_700));
                btnComplete.setEnabled(false);
            } else {
                tvStatus.setText(R.string.pending_ex);
                tvStatus.setTextColor(ContextCompat.getColor(this.requireContext(),
                        R.color.amber_700));
                btnComplete.setEnabled(true);
            }
        } else {
            tvStatus.setText(R.string.future_ex);
            tvStatus.setTextColor(ContextCompat.getColor(this.requireContext(),
                    R.color.blue_700));
            btnComplete.setEnabled(false);
        }
    }

    private boolean getStatus() {
        switch (pos) {
            case 0: {
                return log.isMorning();
            }
            case 1: {
                return log.isNoon();
            }
            case 2: {
                return log.isAfternoon();
            }
            case 3: {
                return log.isEvening();
            }
            case 4: {
                return log.isNight();
            }
            default: {
                return true;
            }
        }
    }

    private void showEx() {
        Set<String> ex = sharedPreferences.getStringSet(partEx[pos],
                new HashSet<>());
        String str = setToString(ex);

        if (str.equals("")) {
            tvOpera.setText(R.string.no_ex);
        } else {
            tvOpera.setText(R.string.has_ex);
            tvEx.setText(str);
        }
    }

    private String setToString(Set<String> set) {
        List<Ex> exes = new ArrayList<>();
        String[] arr = set.toArray(new String[0]);

        for (String s : arr) {
            int id = Integer.parseInt(s);
            Ex ex = exHelper.getEx(id);
            if (ex.getId() >= 0) {
                exes.add(ex);
            }
        }

        String result = "";

        if (!exes.isEmpty()) {
            for (Ex ex: exes) {
                result += String.valueOf(ex.getItem()) + " ";
                result += ex.getName() + "\n";
            }
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }
}
