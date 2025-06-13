package com.example.androidproject.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnRandomExam, btnExamSet, btnCriticalQuiz, btnTopWQuiz,btnWQuizReview,btnQuizPractice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //dbHelper.populateInitialData();
        btnRandomExam = findViewById(R.id.btnRandomExam);
        btnExamSet = findViewById(R.id.btnExamSet);
        btnCriticalQuiz = findViewById(R.id.btnCriticalQuiz);
        btnTopWQuiz = findViewById(R.id.btnTopWQuiz);
        btnWQuizReview = findViewById(R.id.btnWQuizReview);
        btnQuizPractice = findViewById(R.id.btnQuizPractice);
//        imageView = findViewById(R.id.imageView);
//        String imageName = "truy_kich";
//
//// Get the resource ID dynamically
//        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
//
//// Check if it exists
//        if (resId != 0) {
//            imageView.setImageResource(resId);
//        } else {
////            // Handle missing image (fallback or error)
////            imageView.setImageResource(R.drawable.placeholder);
//        }
        btnRandomExam.setOnClickListener(v -> {
           Intent intent = new Intent(MainActivity.this, QuizActivity.class);
           intent.putExtra("quiz_mode", "random_exam");
           startActivity(intent);
        });
        btnExamSet.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });
        btnCriticalQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("quiz_mode", "critical_quiz");
            startActivity(intent);
        });
        btnTopWQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("quiz_mode", "top_wquiz");
            startActivity(intent);
        });
        btnWQuizReview.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("quiz_mode", "wquiz_review");
            startActivity(intent);
        });
        btnQuizPractice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });


    }
}