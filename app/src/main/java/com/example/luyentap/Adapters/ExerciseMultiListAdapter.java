package com.example.luyentap.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luyentap.Helpers.TblExHelper;
import com.example.luyentap.Models.Ex;
import com.example.luyentap.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.luyentap.Utilities.SharePrefUtil.positionToKey;

public class ExerciseMultiListAdapter extends RecyclerView.Adapter<ExerciseMultiListAdapter.DataViewHolder> {

    private SharedPreferences sharedPreferences;
    private TblExHelper helper;
    private final Context context;
    private final String[] part;
    private final String[] time;

    private List<List<Ex>> list = new ArrayList<>();
    private List<Ex> allEx = new ArrayList<>();

    public ExerciseMultiListAdapter(Context context, String[] part,
                                    String[] time) {
        this.context = context;
        this.part = part;
        this.time = time;
        sharedPreferences = context.getSharedPreferences(context.getResources()
                .getString(R.string.app_shared_pref), Context.MODE_PRIVATE);
        helper = new TblExHelper(context);

        for (int i = 0; i < 5; ++i) {
            addToList(positionToKey(i));
        }

        allEx = helper.getAllEx();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_multi,
                parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        List<Ex> exes = list.get(position);
        String str = "";

        if (exes.isEmpty()) {
            str = context.getResources().getString(R.string.no_ex_list);
        } else {
            for (Ex ex: exes) {
                str += String.valueOf(ex.getItem()) + " ";
                str += ex.getName() + "\n";
            }
            str = str.substring(0, str.length() - 1);
        }

        holder.tvPart.setText(part[position]);
        holder.tvTime.setText(time[position]);
        holder.tvEx.setText(str);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allEx = helper.getAllEx();

                List<String> exName = new ArrayList<>();
                for (Ex ex: allEx) {
                    exName.add(ex.getName());
                }
                String[] exNameArr = exName.toArray(new String[0]);

                List<Ex> choices = new ArrayList<>();

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMultiChoiceItems(exNameArr, null, (dialog1, which, isChecked) -> {
                    if (isChecked) {
                        choices.add(allEx.get(which));
                    }
                });
                dialog.setPositiveButton(R.string.btn_accept, (dialog12, which) -> {
                    saveEx(choices, position);
                    updateData();
                });
                dialog.setNegativeButton(R.string.btn_cancel, (dialog13, which) -> {});
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private void addToList(String key) {
        List<Ex> exes = new ArrayList<>();
        Set<String> set = sharedPreferences.getStringSet(key, new HashSet<>());
        String[] arr = set.toArray(new String[0]);

        for (String s : arr) {
            int id = Integer.parseInt(s);
            Ex ex = helper.getEx(id);

            if (ex.getId() >= 0) {
                exes.add(ex);
            }
        }

        list.add(exes);
    }

    public void updateData() {
        list.clear();
        for (int i = 0; i < 5; ++i) {
            addToList(positionToKey(i));
        }

        notifyDataSetChanged();
    }

    private void saveEx(List<Ex> exes, int position) {
        Set<String> set = new HashSet<>();

        for (Ex ex: exes) {
            set.add(String.valueOf(ex.getId()));
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(positionToKey(position), set);
        editor.apply();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvPart;
        private final TextView tvTime;
        private final TextView tvEx;
        private final MaterialButton btnEdit;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPart = itemView.findViewById(R.id.tv_part_exercise_multi_item);
            tvTime = itemView.findViewById(R.id.tv_time_exercise_multi_item);
            tvEx = itemView.findViewById(R.id.tv_ex_exercise_multi_item);
            btnEdit = itemView.findViewById(R.id.btn_edit_exercise_multi_item);
        }
    }
}
