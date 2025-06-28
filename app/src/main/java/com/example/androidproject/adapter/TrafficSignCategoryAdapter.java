package com.example.androidproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.model.TrafficSignCategory;

import java.util.List;

public class TrafficSignCategoryAdapter extends RecyclerView.Adapter<TrafficSignCategoryAdapter.ViewHolder> {

    private List<TrafficSignCategory> categoryList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TrafficSignCategory category);
    }

    public TrafficSignCategoryAdapter(List<TrafficSignCategory> categoryList, OnItemClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_traffic_sign_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrafficSignCategory category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(category));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}


