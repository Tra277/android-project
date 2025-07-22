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
import android.webkit.URLUtil;

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
    private static final String ARG_IS_REVIEW_MODE = "is_review_mode";
    private static final String ARG_SELECTED_ANSWER_ID = "selected_answer_id";

    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private Question question;
    private List<Answer> answers;
    private OnAnswerSubmittedListener listener;
    private RadioGroup rgAnswers;
    private boolean isReviewMode;
    private int selectedAnswerId;
    private TextView tvExplanation;

    public static QuestionFragment newInstance(int questionId, boolean isReviewMode, int selectedAnswerId) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_ID, questionId);
        args.putBoolean(ARG_IS_REVIEW_MODE, isReviewMode);
        args.putInt(ARG_SELECTED_ANSWER_ID, selectedAnswerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionDAO = new QuestionDAO(requireContext());
        answerDAO = new AnswerDAO(requireContext());
        int questionId = getArguments().getInt(ARG_QUESTION_ID);
        isReviewMode = getArguments().getBoolean(ARG_IS_REVIEW_MODE);
        selectedAnswerId = getArguments().getInt(ARG_SELECTED_ANSWER_ID);

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
        tvExplanation = view.findViewById(R.id.tv_explanation);

        // Set question content
        if (question != null && !question.getContent().isEmpty()) {
            tvQuestionContent.setText(question.getContent());
        } else {
            tvQuestionContent.setText("No question available");
            Log.e("QuestionContent", "Question content is empty or null");
        }

        // Load question image
        if (question != null && question.getImagePath() != null && !question.getImagePath().isEmpty()) {
            ivQuestionImage.setVisibility(View.VISIBLE);
            try {
                if (URLUtil.isValidUrl(question.getImagePath())) {
                    Glide.with(this)
                            .load(question.getImagePath())
                            .into(ivQuestionImage);
                } else {
                    Glide.with(this)
                            .load(getImageResource(question.getImagePath()))
                            .into(ivQuestionImage);
                }
            } catch (Exception e) {
                ivQuestionImage.setVisibility(View.GONE);
                Log.e("QuestionFragment", "Error loading question image: ", e);
            }
        } else {
            ivQuestionImage.setVisibility(View.GONE); // Hide the ImageView if imagePath is null or empty
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

            if (answer.getImagePath() != null && !answer.getImagePath().isEmpty()) {
                ImageView iv = new ImageView(requireContext());
                try {
                    if (URLUtil.isValidUrl(answer.getImagePath())) {
                        Glide.with(this)
                                .load(answer.getImagePath())
                                .into(iv);
                    } else {
                        Glide.with(this)
                                .load(getImageResource(answer.getImagePath()))
                                .into(iv);
                    }
                    rgAnswers.addView(iv);
                } catch (Exception e) {
                    Log.e("QuestionFragment", "Error loading answer image: ", e);
                }
            }
        }

        // Add RadioGroup to container
        llAnswersContainer.addView(rgAnswers);
        answerDAO = new AnswerDAO(requireContext());
        if (isReviewMode) {
            rgAnswers.setEnabled(false); // Disable interaction in review mode
            for (int i = 0; i < rgAnswers.getChildCount(); i++) {
                View child = rgAnswers.getChildAt(i);
                if (child instanceof RadioButton) {
                    RadioButton rb = (RadioButton) child;
                    rb.setEnabled(false); // Disable individual radio buttons
                    Answer correctAnswer = answerDAO.getCorrectAnswerByQuestionId(question.getId());
                    int answerId = (int) rb.getTag();
                    if (answerId == selectedAnswerId && question.getQuestionStatus().equals("not_yet_done")) {
                        rb.setChecked(true); // Select the user's chosen answer
                        if (correctAnswer.getId() != selectedAnswerId) {
                            rb.setBackgroundResource(R.drawable.incorrect_answer_background); // Highlight incorrect user choice
                        }
                    }
                    if (answerId == correctAnswer.getId()) {
                        rb.setBackgroundResource(R.drawable.correct_answer_background); // Highlight correct answer
                    }
                }
            }
            // Show explanation
            if (question != null && question.getQuestionExplanation() != null && !question.getQuestionExplanation().isEmpty()) {
                tvExplanation.setVisibility(View.VISIBLE);
                tvExplanation.setText("Giải thích: " + question.getQuestionExplanation());
            } else {
                tvExplanation.setVisibility(View.GONE);
            }
        } else {
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
                        questionDAO.updateQuestionSelectedAnswerId(question.getId(), answerId);
                        listener.onAnswerSubmitted(question.getId(), status);
                    }
                }
            });
            tvExplanation.setVisibility(View.GONE); // Hide explanation in normal quiz mode
        }

        return view;
    }

    private int getImageResource(String imagePath) {
        return getResources().getIdentifier(imagePath.replace(".png", ""), "drawable", requireContext().getPackageName());
    }
}
