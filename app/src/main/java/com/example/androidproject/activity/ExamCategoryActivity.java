package com.example.androidproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.adapter.CategoryAdapter;
import com.example.androidproject.dao.CategoryDAO;
import com.example.androidproject.dao.DrivingLicenseDAO;
import com.example.androidproject.model.Category;
import com.example.androidproject.model.DrivingLicense;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ExamCategoryActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {

    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryDAO categoryDAO;
    private int licenseId;
    private String licenseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exam_category);


        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_exit); // Or your back arrow icon
        }

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryDAO = new CategoryDAO(this);

       SharedPreferences prefs = getSharedPreferences("LicensePrefs", MODE_PRIVATE);
        licenseCode = prefs.getString("selectedLicenseCode", "A1");
        DrivingLicenseDAO drivingLicenseDAO = new DrivingLicenseDAO(this);
        DrivingLicense license = drivingLicenseDAO.getDrivingLicenseByCode(licenseCode);
        if (license == null) {
            Log.e("ExamCategoryActivity", "License not found for code: " + licenseCode);
            return;
        }
        licenseId = license.getId();
        loadCategories();
    }

    private void loadCategories() {
        List<Category> categories = categoryDAO.getCategoriesWithQuestionCountsByLicenseId(licenseId);
        categoryAdapter = new CategoryAdapter(categories, this);
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onCategoryClick(Category category) {
         Intent intent = new Intent(ExamCategoryActivity.this, QuizActivity.class);
         intent.putExtra("quiz_mode", "category");
         intent.putExtra("categoryId", category.getId());
         startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Handle the back button click
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
