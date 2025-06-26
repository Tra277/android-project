package com.example.androidproject.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.adapter.QuestionGridAdapter;
import com.example.androidproject.model.Question;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class QuestionBottomSheetFragment extends BottomSheetDialogFragment implements QuestionGridAdapter.OnQuestionClickListener {

    private static final String ARG_QUESTIONS = "questions";
    private List<Question> questionList;
    private OnQuestionSelectedListener listener;

    public static QuestionBottomSheetFragment newInstance(ArrayList<Question> questions) {
        QuestionBottomSheetFragment fragment = new QuestionBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTIONS, questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnQuestionSelectedListener) {
            listener = (OnQuestionSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnQuestionSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionList = (List<Question>) getArguments().getSerializable(ARG_QUESTIONS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_bottom_sheet, container, false);

        RecyclerView questionGridRecyclerView = view.findViewById(R.id.questionGridRecyclerView);
        questionGridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6)); // 6 columns
        QuestionGridAdapter adapter = new QuestionGridAdapter(questionList, this);
        questionGridRecyclerView.setAdapter(adapter);

        Button btnSheetClose = view.findViewById(R.id.btn_sheet_close);
        btnSheetClose.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onQuestionClick(int position) {
        if (listener != null) {
            listener.onQuestionSelected(position);
            dismiss(); // Close the bottom sheet after selection
        }
    }

    public interface OnQuestionSelectedListener {
        void onQuestionSelected(int position);
    }
}
