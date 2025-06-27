package com.example.androidproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private OnCategoryClickListener listener;

    public CategoryAdapter(List<Category> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryNameTextView.setText(category.getName());
        holder.categoryDescriptionTextView.setText(category.getDescription());
        // You would typically get total and done questions from a DAO or service
        // For now, setting placeholders
        holder.totalQuestionsTextView.setText("Total Questions: " + category.getTotalQuestions());
        holder.doneQuestionsTextView.setText("Done Questions: " + category.getDoneQuestions());

        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView;
        TextView categoryDescriptionTextView;
        TextView totalQuestionsTextView;
        TextView doneQuestionsTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            categoryDescriptionTextView = itemView.findViewById(R.id.categoryDescriptionTextView);
            totalQuestionsTextView = itemView.findViewById(R.id.totalQuestionsTextView);
            doneQuestionsTextView = itemView.findViewById(R.id.doneQuestionsTextView);
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }
}
