package com.example.androidproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.stream.Collectors;

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
    private List<String> questionStatuses;

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
        questionStatuses = intent.getStringArrayListExtra("question_statuses");
        List<Question> questions = intent.getParcelableArrayListExtra("questions");

        // Initialize views
        Button returnToMainButton = findViewById(R.id.returnToMainButton);
        resultMessageTextView = findViewById(R.id.resultMessageTextView);

        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Check for critical quiz questions answered incorrectly
        boolean failedCriticalQuiz = questions.stream()
                .filter(Question::isCriticalQuiz)
                .anyMatch(q -> !q.getQuestionStatus().equals("correct"));

        // Set result message
        String resultMessage;
        if (failedCriticalQuiz) {
            resultMessage = "KHÔNG ĐẠT: SAI CÂU ĐIỂM LIỆT!";
        } else if (correctAnswers > 20) {
            resultMessage = "Bạn đã đạt";
        } else {
            resultMessage = "KHÔNG ĐẠT: CHƯA ĐỦ SỐ ĐIỂM";
        }
        resultMessageTextView.setText(resultMessage);

        // Set result data
        timeTakenTextView.setText(timeTaken);
        correctAnswersTextView.setText(correctAnswers + "/" + totalQuestions);
        incorrectAnswersTextView.setText(String.valueOf(totalQuestions - correctAnswers));
        totalQuestionsTextView.setText(String.valueOf(totalQuestions));

        // Set adapter for GridView
        QuestionAdapter adapter = new QuestionAdapter(this, questionStatuses);
        questionsGridView.setAdapter(adapter);

        questionsGridView.setOnItemClickListener((parent, view, position, id) -> {
            Question question = questions.get(position);
            new AlertDialog.Builder(this)
                    .setTitle(question.getContent())
                    .setMessage(question.getQuestionExplanation())
                    .setPositiveButton("OK", null)
                    .show();
        });
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                Intent licenseIntent = new Intent(ResultActivity.this, LicenseActivity.class);
                startActivity(licenseIntent);
                Toast.makeText(this, "Menu được nhấn", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Call superclass method
        // Do nothing
    }
}


