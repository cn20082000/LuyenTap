package com.example.luyentap.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luyentap.Helpers.TblExHelper;
import com.example.luyentap.Models.Ex;
import com.example.luyentap.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSingleListAdapter extends RecyclerView.Adapter<ExerciseSingleListAdapter.DataViewHolder> {

    private final Context context;
    private ViewGroup parent;
    private final TblExHelper helper;

    private List<Ex> list = new ArrayList<>();

    public ExerciseSingleListAdapter(Context context) {
        this.context = context;
        helper = new TblExHelper(context);
        list = helper.getAllEx();
    }

    @NonNull
    @Override
    public ExerciseSingleListAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                       int viewType) {
        this.parent = parent;
        switch (viewType) {
            case 0: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_single,
                        parent, false);
                return new ExerciseSingleListAdapter.DataViewHolder(view);
            }
            default: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty,
                        parent, false);
                return new ExerciseSingleListAdapter.DataViewHolder(view);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= list.size()) {
            return 1;
        }
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExerciseSingleListAdapter.DataViewHolder holder, int position) {
        if (position < list.size()) {
            holder.tvName.setText(list.get(position).getName());
            holder.tvItem.setText("Số lượng: " + list.get(position).getItem());
            holder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenu(holder.btnMore, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public void updateData() {
        list = helper.getAllEx();
        notifyDataSetChanged();
    }

    private void showMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_ex_single_item,
                popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.btn_edit_name_ex_single_item: {
                        View dialogEditName = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_edit_ex,
                                parent, false);
                        TextInputLayout edtName =
                                dialogEditName.findViewById(R.id.edt_custom_edit_ex);
                        edtName.setHint(R.string.ex_name);
                        edtName.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);

                        int maxLength = 30;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        edtName.getEditText().setFilters(fArray);

                        AlertDialog.Builder alert =
                                new AlertDialog.Builder(context);
                        alert.setTitle(R.string.menu_ex_name);
                        alert.setView(dialogEditName);

                        alert.setPositiveButton("Đồng ý",
                                (dialog, which) -> {
                                    String name = edtName.getEditText().getText().toString();
                                    list.get(position).setName(name);
                                    helper.updateEx(list.get(position));
                                    notifyItemChanged(position);
                                });

                        alert.setNegativeButton("Hủy", (dialog, which) -> {});

                        alert.show();
                        break;
                    }
                    case R.id.btn_edit_item_ex_single_item: {
                        View dialogEditName = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_edit_ex,
                                        parent, false);
                        TextInputLayout edtItem =
                                dialogEditName.findViewById(R.id.edt_custom_edit_ex);
                        edtItem.setHint(R.string.ex_item);
                        edtItem.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

                        int maxLength = 5;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        edtItem.getEditText().setFilters(fArray);

                        AlertDialog.Builder alert =
                                new AlertDialog.Builder(context);
                        alert.setTitle(R.string.menu_ex_item);
                        alert.setView(dialogEditName);

                        alert.setPositiveButton("Đồng ý",
                                (dialog, which) -> {
                                    int it = Integer.parseInt(edtItem
                                            .getEditText().getText().toString());
                                    list.get(position).setItem(it);
                                    helper.updateEx(list.get(position));
                                    notifyItemChanged(position);
                                });

                        alert.setNegativeButton("Hủy", (dialog, which) -> {});

                        alert.show();
                        break;
                    }
                    case R.id.btn_delete_ex_single_item: {
                        AlertDialog.Builder alert =
                                new AlertDialog.Builder(context);
                        alert.setTitle(R.string.menu_ex_delete);

                        alert.setPositiveButton("Đồng ý",
                                (dialog, which) -> {
                                    helper.deleteEx(list.get(position));
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                });

                        alert.setNegativeButton("Hủy", (dialog, which) -> {});

                        alert.show();
                        break;
                    }
                }

                return true;
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });

        popupMenu.show();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final TextView tvItem;
        private final ImageView btnMore;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_exercise_single_item);
            tvItem = itemView.findViewById(R.id.tv_item_exercise_single_item);
            btnMore = itemView.findViewById(R.id.btn_more_exercise_single_item);
        }
    }
}

