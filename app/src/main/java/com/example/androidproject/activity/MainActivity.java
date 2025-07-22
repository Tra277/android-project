package com.example.androidproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.androidproject.R;
import com.example.androidproject.dao.DrivingLicenseDAO;
import com.example.androidproject.db.DatabaseHelper;
import com.example.androidproject.model.DrivingLicense;

public class MainActivity extends BaseActivity {
    ImageView imageView;
    Button btnRandomExam, btnExamSet, btnCriticalQuiz, btnTopWQuiz,btnWQuizReview,btnQuizPractice, btnTrafficSigns;
    private ActivityResultLauncher<Intent> licenseActivityResultLauncher;
    private DrivingLicenseDAO drivingLicenseDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        drivingLicenseDAO = new DrivingLicenseDAO(this);
        SharedPreferences prefs = getSharedPreferences("LicensePrefs", MODE_PRIVATE);
        String licenseCode = prefs.getString("selectedLicenseCode", "A1");
        DrivingLicense license = drivingLicenseDAO.getDrivingLicenseByCode(licenseCode);
        //dbHelper.populateInitialData();
        setSupportActionBar(toolbar);
        if(license != null) {
            toolbar.setTitle(license.getDescription());
        }
        toolbar.setNavigationIcon(null); // ←
        toolbar.inflateMenu(R.menu.top_app_bar_menu); // menu góc phải
        btnRandomExam = findViewById(R.id.btnRandomExam);
        btnExamSet = findViewById(R.id.btnExamSet);
        btnCriticalQuiz = findViewById(R.id.btnCriticalQuiz);
        btnTopWQuiz = findViewById(R.id.btnTopWQuiz);
        btnWQuizReview = findViewById(R.id.btnWQuizReview);
        btnQuizPractice = findViewById(R.id.btnQuizPractice);
        btnTrafficSigns = findViewById(R.id.btnTrafficSigns);

        licenseActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String licenseDescription = result.getData().getStringExtra("licenseDescription");
                        if (licenseDescription != null) {
                            toolbar.setTitle(licenseDescription);
                        }
                    }
                }
        );

        btnRandomExam.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("quiz_mode", "random_exam");
            startActivity(intent);
        });
        btnExamSet.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, ExamSetActivity.class);
            startActivity(intent1);
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
            Intent intent = new Intent(MainActivity.this, ExamCategoryActivity.class);
            startActivity(intent);
        });
        btnTrafficSigns.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignsDashboardActivity.class);
            startActivity(intent);
        });
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
                licenseActivityResultLauncher.launch(intent);
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
}


