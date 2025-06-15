package com.example.androidproject.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.dao.QuestionDAO;
import com.example.androidproject.model.Question;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

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

        questionDAO = new QuestionDAO(this);

        //Get data from intent
        Intent intent = getIntent();
        long examSetId = intent.getLongExtra("exam_set_id", -1);
        long totalQuestions = intent.getIntExtra("total_questions",-1);
        if (examSetId != -1) {
            // Do something with the examSetId
        }
        List<Question> questions = questionDAO.getQuestionsByExamSetId(examSetId);

    }
}