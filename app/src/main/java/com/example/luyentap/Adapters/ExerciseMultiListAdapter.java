package com.example.luyentap.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luyentap.R;
import com.google.android.material.button.MaterialButton;

public class ExerciseMultiListAdapter extends RecyclerView.Adapter<ExerciseMultiListAdapter.DataViewHolder> {

//    private List<Type> list;
    private final Context context;
    private final String[] part;
    private final String[] time;

    public ExerciseMultiListAdapter(Context context, String[] part,
                                    String[] time) {
        this.context = context;
        this.part = part;
        this.time = time;
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
        holder.tvPart.setText(part[position]);
        holder.tvTime.setText(time[position]);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

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
