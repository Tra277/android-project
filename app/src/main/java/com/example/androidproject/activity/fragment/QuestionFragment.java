package com.example.androidproject.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androidproject.OnAnswerSubmittedListener;
import com.example.androidproject.R;
import com.example.androidproject.dao.AnswerDAO;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Answer;
import com.example.androidproject.model.Question;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION_ID = "question_id";
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private Question question;
    private List<Answer> answers;
    private OnAnswerSubmittedListener listener;
    RadioGroup rgAnswers;

    public static QuestionFragment newInstance(int questionId) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_ID, questionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionDAO = new QuestionDAO(requireContext());
        answerDAO = new AnswerDAO(requireContext());
        int questionId = getArguments().getInt(ARG_QUESTION_ID);
        question = questionDAO.getQuestionById(questionId);
        answers = answerDAO.getAnswersByQuestionId(questionId);
        // Set the listener
        if (getActivity() instanceof OnAnswerSubmittedListener) {
            listener = (OnAnswerSubmittedListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        Log.e("QuestionContent", question.getContent());
        TextView tvQuestionContent = view.findViewById(R.id.tv_question_content);
        ImageView ivQuestionImage = view.findViewById(R.id.iv_question_image);
        FrameLayout llAnswersContainer = view.findViewById(R.id.ll_answers_container);
        Button btnSubmit = view.findViewById(R.id.btn_submit);

        // Set question content
        tvQuestionContent.setText(question.getContent());

        // Load question image if path exists
        if (!question.getImagePath().isEmpty()) {
            ivQuestionImage.setVisibility(View.VISIBLE);
            try {
                Glide.with(this)
                        .load(getImageResource(question.getImagePath()))
                        .into(ivQuestionImage);
            } catch (Exception e) {
                ivQuestionImage.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }
        llAnswersContainer.removeAllViews();

// Tạo RadioGroup
        RadioGroup rgAnswers = new RadioGroup(requireContext());
        rgAnswers.setOrientation(LinearLayout.VERTICAL);

        for (Answer answer : answers) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(answer.getContent());
            rgAnswers.addView(radioButton);

            if (!answer.getImagePath().isEmpty()) {
                ImageView iv = new ImageView(requireContext());
                // Load image with Glide
                rgAnswers.addView(iv);
            }
        }

        llAnswersContainer.addView(rgAnswers);
        btnSubmit.setOnClickListener(v -> {
            int checkedId = rgAnswers.getCheckedRadioButtonId();
            if (checkedId != -1) {
                RadioButton selectedRadio = view.findViewById(checkedId);
                int answerId = (int) selectedRadio.getTag();
                Answer selectedAnswer = answers.stream()
                        .filter(a -> a.getId() == answerId)
                        .findFirst()
                        .orElse(null);
                if (selectedAnswer != null) {
                    String status = selectedAnswer.isCorrect() ? "correct" : "incorrect";
                    if (listener != null) {
                        listener.onAnswerSubmitted(question.getId(), status);
                    }
                }
            }
        });

        return view;
    }

    private int getImageResource(String imagePath) {
        return getResources().getIdentifier(imagePath.replace(".png", ""), "drawable", requireContext().getPackageName());
    }
}