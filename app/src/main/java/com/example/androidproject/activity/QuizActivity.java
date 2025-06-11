package com.example.androidproject.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidproject.OnAnswerSubmittedListener;
import com.example.androidproject.R;
import com.example.androidproject.adapter.QuestionPagerAdapter;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Question;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class QuizActivity extends AppCompatActivity implements OnAnswerSubmittedListener {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView tvTimer;
    private ProgressBar progressBar;
    private QuestionDAO questionDAO;
    private List<Question> questions;
    private CountDownTimer countDownTimer;
    private int totalQuestions;
    private int completedQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tvTimer = findViewById(R.id.tv_timer);
        progressBar = findViewById(R.id.progress_bar);

        // Initialize DAO and load questions
        questionDAO = new QuestionDAO(this);
        questions = questionDAO.getAllQuestions();
        totalQuestions = questions.size();
        completedQuestions = (int) questions.stream()
                .filter(q -> !q.getQuestionStatus().equals("not_yet_done"))
                .count();

        // Set up ViewPager and TabLayout
        QuestionPagerAdapter adapter = new QuestionPagerAdapter(this);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Câu " + (position + 1)))
                .attach();
        updateProgress();
        // Set up timer (e.g., 20 minutes = 1800000 * 2 / 3 ms)
        countDownTimer = new CountDownTimer(1800000 * 2 / 3, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                tvTimer.setText(String.format("Time: %02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Time's up!");
                // Handle quiz completion
            }
        }.start();
    }
    @Override
    public void onAnswerSubmitted(int questionId, String status) {
        // Update the question status in the database
        Question question = questionDAO.getQuestionById(questionId);
        if (question != null) {
            question.setQuestionStatus(status);
            questionDAO.updateQuestion(question);

            // Update completedQuestions count
            completedQuestions = (int) questions.stream()
                    .filter(q -> !q.getQuestionStatus().equals("not_yet_done"))
                    .count();

            // Update progress
            updateProgress();

            // Refresh TabLayout to show updated status
            //refreshTabs();
        }
    }

    public void updateProgress() {
        completedQuestions = (int) questionDAO.getAllQuestions().stream()
                .filter(q -> !q.getQuestionStatus().equals("not_yet_done"))
                .count();
        int progress = (int) ((completedQuestions / (float) totalQuestions) * 100);
        progressBar.setProgress(progress);
    }

    private void refreshTabs() {
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Câu " + (position + 1) ));
        mediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}