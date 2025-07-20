package com.example.androidproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidproject.R;
import com.example.androidproject.model.LicenseCategoryItem;
import java.util.List;

public class LicensesAdapter extends RecyclerView.Adapter<LicensesAdapter.LicenseViewHolder> {
    private List<LicenseCategoryItem> items;
    private int selectedPosition = -1; // -1 means no item is selected
    private OnItemClickListener listener;

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(int position, LicenseCategoryItem item);
    }

    public LicensesAdapter(List<LicenseCategoryItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public static class LicenseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvShortCode;
        public TextView tvTitle;
        public TextView tvDescription;

        public LicenseViewHolder(View itemView) {
            super(itemView);
            tvShortCode = itemView.findViewById(R.id.tvShortCode);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }

    @Override
    public LicenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_license, parent, false);
        return new LicenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LicenseViewHolder holder, int position) {
        LicenseCategoryItem item = items.get(position);
        holder.tvShortCode.setText(item.getShortCode());
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());

        // Apply selected state
        holder.itemView.setSelected(position == selectedPosition);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition); // Refresh previous item
            notifyItemChanged(selectedPosition); // Refresh current item
            if (listener != null) {
                listener.onItemClick(position, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Method to programmatically set the selected item
    public void setSelectedItem(int position) {
        if (position >= 0 && position < items.size()) {
            int previousPosition = selectedPosition;
            selectedPosition = position;
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            if (selectedPosition != -1) {
                notifyItemChanged(selectedPosition);
            }
        }
    }

    // Method to get the currently selected position
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int findPositionByCode(String code) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getShortCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }
}