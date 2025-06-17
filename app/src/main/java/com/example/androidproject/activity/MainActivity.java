package com.example.androidproject.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.androidproject.R;
import com.example.androidproject.db.DatabaseHelper;

public class MainActivity extends BaseActivity {
    ImageView imageView;
    Button btnRandomExam, btnExamSet, btnCriticalQuiz, btnTopWQuiz,btnWQuizReview,btnQuizPractice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //dbHelper.populateInitialData();
        setSupportActionBar(toolbar);
        toolbar.setTitle("600 câu hỏi ôn thi GPLX");
        toolbar.setNavigationIcon(null); // ←

        toolbar.inflateMenu(R.menu.top_app_bar_menu); // menu góc phải


        btnRandomExam = findViewById(R.id.btnRandomExam);
        btnExamSet = findViewById(R.id.btnExamSet);
        btnCriticalQuiz = findViewById(R.id.btnCriticalQuiz);
        btnTopWQuiz = findViewById(R.id.btnTopWQuiz);
        btnWQuizReview = findViewById(R.id.btnWQuizReview);
        btnQuizPractice = findViewById(R.id.btnQuizPractice);

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
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
                startActivity(intent);
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
        //dbHelper.populateInitialData();
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


    }
}