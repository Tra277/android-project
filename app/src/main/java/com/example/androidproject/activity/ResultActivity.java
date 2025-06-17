package com.example.androidproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.adapter.QuestionAdapter;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Question;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity {

    private TextView resultTitleTextView;
    private TextView resultMessageTextView;
    private TextView timeTakenTextView;
    private TextView correctAnswersTextView;
    private TextView incorrectAnswersTextView;
    private TextView totalQuestionsTextView;
    private GridView questionsGridView;
    private QuestionDAO questionDAO;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setSupportActionBar(toolbar);
        toolbar.setTitle("Kết quả bài thi");
        toolbar.setNavigationIcon(null); // ←

        toolbar.inflateMenu(R.menu.top_app_bar_menu); // menu góc phải

        // Initialize views
        resultTitleTextView = findViewById(R.id.resultTitleTextView);
        resultMessageTextView = findViewById(R.id.resultMessageTextView);
        timeTakenTextView = findViewById(R.id.timeTakenTextView);
        correctAnswersTextView = findViewById(R.id.correctAnswersTextView);
        incorrectAnswersTextView = findViewById(R.id.incorrectAnswersTextView);
        totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView);
        questionsGridView = findViewById(R.id.questionsGridView);

        questionDAO = new QuestionDAO(this);

        // Get data from intent
        Intent intent = getIntent();
        long examSetId = intent.getLongExtra("exam_set_id", -1);
        int totalQuestions = intent.getIntExtra("total_questions", -1);
        int correctAnswers = intent.getIntExtra("correct_answers", 0);
        int incorrectAnswers = intent.getIntExtra("incorrect_answers", 0);
        String timeTaken = intent.getStringExtra("time_taken");
        ArrayList<Integer> questionStatuses = intent.getIntegerArrayListExtra("question_statuses");

        // Set result data
        timeTakenTextView.setText(timeTaken);
        correctAnswersTextView.setText(correctAnswers + "/" + totalQuestions);
        incorrectAnswersTextView.setText(String.valueOf(incorrectAnswers));
        totalQuestionsTextView.setText(String.valueOf(totalQuestions));

        // Set adapter for GridView
        QuestionAdapter adapter = new QuestionAdapter(this, questionStatuses);
        questionsGridView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Xử lý khi nhấn vào menu cài đặt
            // Ví dụ: Mở màn hình cài đặt
            // Intent intent = new Intent(this, SettingsActivity.class);
            // startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
