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
    private RadioGroup rgAnswers; // Field to store the programmatically created RadioGroup

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
        Button btnSubmit = view.findViewById(R.id.btn_submit);

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
            radioButton.setTag(answer.getId()); // Set tag to answer ID
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

        // Set submit button listener
        rgAnswers.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) { // A RadioButton is selected
                RadioButton selectedRadio = group.findViewById(checkedId);
                Integer answerId = (Integer) selectedRadio.getTag();
                Answer selectedAnswer = answers.stream()
                        .filter(a -> a.getId() == answerId)
                        .findFirst()
                        .orElse(null);
                if (selectedAnswer != null && listener != null) {
                    String status = selectedAnswer.isCorrect() ? "correct" : "incorrect";
                    listener.onAnswerSubmitted(question.getId(), status);
//                    // Optional: Disable RadioGroup to prevent further changes
//                    rgAnswers.setEnabled(false);
//                    for (int i = 0; i < rgAnswers.getChildCount(); i++) {
//                        rgAnswers.getChildAt(i).setEnabled(false);
//                    }
                }
            }
        });

        // Set submit button listener
        btnSubmit.setOnClickListener(v -> {

        });
        return view;
    }

    private int getImageResource(String imagePath) {
        return getResources().getIdentifier(imagePath.replace(".png", ""), "drawable", requireContext().getPackageName());
    }
}