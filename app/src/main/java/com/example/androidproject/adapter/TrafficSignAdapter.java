package com.example.androidproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.model.TrafficSign;

import java.util.List;

public class TrafficSignAdapter extends RecyclerView.Adapter<TrafficSignAdapter.ViewHolder> {

    private List<TrafficSign> signList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TrafficSign sign);
    }

    public TrafficSignAdapter(List<TrafficSign> signList, OnItemClickListener listener) {
        this.signList = signList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_traffic_sign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrafficSign sign = signList.get(position);
        holder.tvSignCode.setText(sign.getCode());
        holder.tvSignName.setText(sign.getName());

        // Load image using Glide
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                sign.getImagePath(), "drawable", holder.itemView.getContext().getPackageName());
        if (imageResId != 0) {
            Glide.with(holder.itemView.getContext())
                    .load(imageResId)
                    .into(holder.ivSignImage);
        } else {
            // Handle case where image is not found (e.g., set a placeholder)
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.ic_launcher_foreground) // Placeholder image
                    .into(holder.ivSignImage);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(sign));
    }

    @Override
    public int getItemCount() {
        return signList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSignImage;
        TextView tvSignCode;
        TextView tvSignName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSignImage = itemView.findViewById(R.id.ivSignImage);
            tvSignCode = itemView.findViewById(R.id.tvSignCode);
            tvSignName = itemView.findViewById(R.id.tvSignName);
        }
    }
}


