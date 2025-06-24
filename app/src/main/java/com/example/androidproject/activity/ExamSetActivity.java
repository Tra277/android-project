package com.example.androidproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.example.androidproject.R;
import com.example.androidproject.adapter.ExamSetAdapter;
import com.example.androidproject.dao.DrivingLicenseDAO;
import com.example.androidproject.dao.ExamSetDAO;
import com.example.androidproject.model.DrivingLicense;
import com.example.androidproject.model.ExamSet;
import java.util.ArrayList;
import java.util.List;

public class ExamSetActivity extends AppCompatActivity {

    private RecyclerView examSetRecyclerView;
    private ExamSetAdapter examSetAdapter;
    private List<ExamSet> examSetList;
    private ExamSetDAO examSetDAO;
    private DrivingLicenseDAO drivingLicenseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_set);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đề thi Hạng A1");

        examSetRecyclerView = findViewById(R.id.examSetRecyclerView);
        examSetRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        drivingLicenseDAO = new DrivingLicenseDAO(this);
        examSetDAO = new ExamSetDAO(this);
        //Get license shared preferences
        SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
        String license_code = prefs.getString("license_code", "A1");
        DrivingLicense license = drivingLicenseDAO.getDrivingLicenseByCode(license_code);
        examSetList = new ArrayList<>();
        // Add sample data
        examSetList = examSetDAO.getExamSetsByLicenseCode(license.getId());
        examSetAdapter = new ExamSetAdapter(this, examSetList);
        examSetRecyclerView.setAdapter(examSetAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
