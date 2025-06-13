package com.example.androidproject.activity.fragment;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androidproject.OnAnswerSubmittedListener;
import com.example.androidproject.R;
import com.example.androidproject.activity.ResultActivity;
import com.example.androidproject.dao.AnswerDAO;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Answer;
import com.example.androidproject.model.Question;

import java.util.List;

public class QuestionFragment extends Fragment {
    private static final String ARG_QUESTION_ID = "question_id";
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private Question question;
    private List<Answer> answers;
    private OnAnswerSubmittedListener listener;
    private RadioGroup rgAnswers;

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
        if (question == null) {
            Log.e("QuestionFragment", "Question is null for ID: " + questionId);
        }
        if (getActivity() instanceof OnAnswerSubmittedListener) {
            listener = (OnAnswerSubmittedListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView tvQuestionContent = view.findViewById(R.id.tv_question_content);
        ImageView ivQuestionImage = view.findViewById(R.id.iv_question_image);
        FrameLayout llAnswersContainer = view.findViewById(R.id.ll_answers_container);
        //Button btnSubmit = view.findViewById(R.id.btn_submit);

        // Set question content
        if (question != null && !question.getContent().isEmpty()) {
            tvQuestionContent.setText(question.getContent());
        } else {
            tvQuestionContent.setText("No question available");
            Log.e("QuestionContent", "Question content is empty or null");
        }

        // Load question image if path exists
        if (question != null && !question.getImagePath().isEmpty()) {
            ivQuestionImage.setVisibility(View.VISIBLE);
            try {
                Glide.with(this)
                        .load(getImageResource(question.getImagePath()))
                        .into(ivQuestionImage);
            } catch (Exception e) {
                ivQuestionImage.setVisibility(View.GONE);
                Log.e("QuestionFragment", "Error loading question image: ", e);
            }
        }

        // Clear any existing views in the container
        llAnswersContainer.removeAllViews();

        // Create RadioGroup programmatically
        rgAnswers = new RadioGroup(requireContext());
        rgAnswers.setOrientation(LinearLayout.VERTICAL);
        rgAnswers.setPadding(8, 8, 8, 8);

        // Add RadioButtons for each answer
        for (Answer answer : answers) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(answer.getContent());
            radioButton.setTag(answer.getId());
            rgAnswers.addView(radioButton);

            if (!answer.getImagePath().isEmpty()) {
                ImageView iv = new ImageView(requireContext());
                try {
                    Glide.with(this)
                            .load(getImageResource(answer.getImagePath()))
                            .into(iv);
                    rgAnswers.addView(iv);
                } catch (Exception e) {
                    Log.e("QuestionFragment", "Error loading answer image: ", e);
                }
            }
        }

        // Add RadioGroup to container
        llAnswersContainer.addView(rgAnswers);

        // Auto-submit answer when RadioButton is selected
        rgAnswers.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                RadioButton selectedRadio = group.findViewById(checkedId);
                Integer answerId = (Integer) selectedRadio.getTag();
                Answer selectedAnswer = answers.stream()
                        .filter(a -> a.getId() == answerId)
                        .findFirst()
                        .orElse(null);
                if (selectedAnswer != null && listener != null) {
                    String status = selectedAnswer.isCorrect() ? "correct" : "incorrect";
                    listener.onAnswerSubmitted(question.getId(), status);
                }
            }
        });

        // Submit button to complete the quiz
//        btnSubmit.setOnClickListener(v -> {
//            new AlertDialog.Builder(requireContext())
//                    .setTitle("Xác nhận nộp!")
//                    .setMessage("Bạn muốn nộp chứ?")
//                    .setPositiveButton("Vâng!", (dialog, which) -> {
//                        Toast.makeText(requireContext(), "Đã nộp bài!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(requireContext(), ResultActivity.class);
//                        startActivity(intent);
//                    })
//                    .setNegativeButton("Huỷ", null)
//                    .show();
//        });

        return view;
    }

    private int getImageResource(String imagePath) {
        return getResources().getIdentifier(imagePath.replace(".png", ""), "drawable", requireContext().getPackageName());
    }
}