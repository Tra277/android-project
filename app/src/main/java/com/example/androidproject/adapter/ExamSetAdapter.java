package com.example.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidproject.R;
import com.example.androidproject.model.ExamSet;
import com.example.androidproject.activity.QuizActivity;
import java.util.List;
import android.content.Intent;
import android.widget.Toast;

public class ExamSetAdapter extends RecyclerView.Adapter<ExamSetAdapter.ViewHolder> {

    private Context context;
    private List<ExamSet> examSetList;

    public ExamSetAdapter(Context context, List<ExamSet> examSetList) {
        this.context = context;
        this.examSetList = examSetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exam_set, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExamSet examSet = examSetList.get(position);
        holder.examSetNumberTextView.setText(examSet.getName());
        holder.correctAnswersTextView.setText(String.valueOf(examSet.getTotalCorrectAnswer()));
        holder.incorrectAnswersTextView.setText(String.valueOf(examSet.getTotalWrongAnswer()));

        // Set image resources dynamically
        holder.correctAnswersImageView.setImageResource(R.drawable.ic_cau_sai);
        holder.incorrectAnswersImageView.setImageResource(R.drawable.ic_wrong);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizActivity.class);
                // Pass data to QuizActivity
                intent.putExtra("quiz_mode", "exam_set");
                intent.putExtra("examSetId", examSet.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return examSetList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView examSetNumberTextView;
        TextView correctAnswersTextView;
        TextView incorrectAnswersTextView;
        ImageView correctAnswersImageView;
        ImageView incorrectAnswersImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            examSetNumberTextView = itemView.findViewById(R.id.examSetNumberTextView);
            correctAnswersTextView = itemView.findViewById(R.id.correctAnswersTextView);
            incorrectAnswersTextView = itemView.findViewById(R.id.incorrectAnswersTextView);
            correctAnswersImageView = itemView.findViewById(R.id.correctAnswersImageView);
            incorrectAnswersImageView = itemView.findViewById(R.id.incorrectAnswersImageView);
        }
    }
}
