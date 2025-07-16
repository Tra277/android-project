package com.example.androidproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Question;

import java.util.List;

public class QuestionGridAdapter extends RecyclerView.Adapter<QuestionGridAdapter.QuestionViewHolder> {

    private List<Question> questionList;
    private OnQuestionClickListener listener;
    private QuestionDAO questionDAO ;
    public QuestionGridAdapter(List<Question> questionList, OnQuestionClickListener listener) {
        this.questionList = questionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_grid, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        questionDAO = new QuestionDAO(holder.itemView.getContext());
        Question question = questionList.get(position);
        String QuestionNewStatus = questionDAO.getQuestionById(question.getId()).getQuestionStatus();
        holder.questionNumberTextView.setText(String.valueOf(position + 1));

        // Set background based on question status
        if ("correct".equals(QuestionNewStatus) || "incorrect".equals(QuestionNewStatus)) {
            holder.itemView.setActivated(true); // Green background
        } else {
            holder.itemView.setActivated(false); // Default background
        }

        holder.itemView.setOnClickListener(v -> listener.onQuestionClick(position));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionNumberTextView;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNumberTextView = itemView.findViewById(R.id.questionNumberTextView);
        }
    }

    public interface OnQuestionClickListener {
        void onQuestionClick(int position);
    }
}
