package com.example.androidproject.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.example.androidproject.R;
import com.example.androidproject.adapter.ExamSetAdapter;
import com.example.androidproject.model.ExamSet;
import java.util.ArrayList;
import java.util.List;

public class ExamSetActivity extends AppCompatActivity {

    private RecyclerView examSetRecyclerView;
    private ExamSetAdapter examSetAdapter;
    private List<ExamSet> examSetList;

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

        examSetList = new ArrayList<>();
        // Add sample data
        examSetList.add(new ExamSet("Đề thi số 1", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 2", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 3", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 4", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 5", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 6", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 7", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 8", 0, 0,true,1));
        examSetList.add(new ExamSet("Đề thi số 9", 0, 0,true,1));

        examSetAdapter = new ExamSetAdapter(this, examSetList);
        examSetRecyclerView.setAdapter(examSetAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
