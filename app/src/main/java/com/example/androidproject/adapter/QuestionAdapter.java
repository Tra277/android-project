package com.example.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidproject.R;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {

    private Context context;
    private List<String> questionStatuses; // "correct", "incorrect", "not_yet_done"

    public QuestionAdapter(Context context, List<String> questionStatuses) {
        this.context = context;
        this.questionStatuses = questionStatuses;
    }

    @Override
    public int getCount() {
        return questionStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return questionStatuses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        }

        TextView questionTextView = convertView.findViewById(R.id.questionTextView);
        String status = questionStatuses.get(position);

        questionTextView.setText("Câu " + (position + 1));

        switch (status) {
            case "correct":
                questionTextView.setBackgroundColor(context.getResources().getColor(R.color.correct_answer));
                break;
            case "incorrect":
                questionTextView.setBackgroundColor(context.getResources().getColor(R.color.incorrect_answer));
                break;
            default:
                questionTextView.setBackgroundColor(context.getResources().getColor(R.color.not_answered));
                break;
        }

        return convertView;
    }
}
