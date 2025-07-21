package com.example.androidproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.androidproject.fragment.ChatBottomSheetFragment;

public class BaseActivity extends AppCompatActivity {
    protected MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        View baseLayout = getLayoutInflater().inflate(R.layout.base_activity, null);
        FrameLayout contentFrame = baseLayout.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);
        super.setContentView(baseLayout);

        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        // Cho phép override icon back ở Activity con
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        FloatingActionButton fabChat = findViewById(R.id.fabChat);
        if (fabChat != null) {
            fabChat.setOnClickListener(v -> new ChatBottomSheetFragment().show(getSupportFragmentManager(), "chatbot"));
        }
    }
}