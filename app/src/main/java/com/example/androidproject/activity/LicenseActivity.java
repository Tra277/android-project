package com.example.androidproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidproject.R;
import com.example.androidproject.adapter.LicensesAdapter;
import com.example.androidproject.model.LicenseCategoryItem;
import java.util.ArrayList;
import java.util.List;

public class LicenseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private LicensesAdapter adapter;
    private static final String PREFS_NAME = "LicensePrefs";
    private static final String KEY_SELECTED_POSITION = "selectedPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_license);

        // Initialize toolbar from BaseActivity
        setSupportActionBar(toolbar);
        if (toolbar == null) {
            throw new IllegalStateException("Toolbar not found in activity_license.xml");
        }
        toolbar.setTitle("Thiết lập");
        toolbar.setNavigationIcon(null);

        // Set up RecyclerView
        List<LicenseCategoryItem> list = new ArrayList<>();
        list.add(new LicenseCategoryItem("Hạng A", "A", "Mô tô 2 bánh >125cm3 hoặc công suất >11kW."));
        list.add(new LicenseCategoryItem("Hạng B1", "B1", "Ô tô số tự động và xe tương tự hạng A1."));
        list.add(new LicenseCategoryItem("Hạng B", "B", "Ô tô đến 8 chỗ, xe tải đến 3.5 tấn."));
        list.add(new LicenseCategoryItem("Hạng C1", "C1", "Ô tô tải 3.5 - 7.5 tấn, xe môde đến 750 kg."));
        list.add(new LicenseCategoryItem("Hạng C", "C", "Ô tô >7.5 tấn, đầu kéo, chuyên dùng."));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LicensesAdapter(list, (position, item) -> {
            Toast.makeText(this, "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putInt(KEY_SELECTED_POSITION, position).apply();
        });
        recyclerView.setAdapter(adapter);

        // Restore selected position
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedPosition = prefs.getInt(KEY_SELECTED_POSITION, -1);
        if (savedPosition != -1 && savedPosition < list.size()) {
            adapter.setSelectedItem(savedPosition);
        }

        // Set up menu
        toolbar.inflateMenu(R.menu.top_app_bar_menu_exit);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.back) {
                finish();
                return true;
            }
            return false;
        });

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu_exit, menu);
        return true;
    }
}