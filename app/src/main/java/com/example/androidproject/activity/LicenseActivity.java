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
import com.example.androidproject.dao.DrivingLicenseDAO;
import com.example.androidproject.model.DrivingLicense;
import com.example.androidproject.model.LicenseCategoryItem;

import java.util.ArrayList;
import java.util.List;

public class LicenseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private LicensesAdapter adapter;
    private DrivingLicenseDAO drivingLicenseDAO;
    private List<LicenseCategoryItem> licenseCategoryItems; // Lưu danh sách để tìm kiếm
    private static final String PREFS_NAME = "LicensePrefs";
    private static final String KEY_SELECTED_LICENSE_CODE = "selectedLicenseCode";

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

        // Initialize DAO
        drivingLicenseDAO = new DrivingLicenseDAO(this);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadDrivingLicenses(); // Load data from database

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

    private void loadDrivingLicenses() {
        List<DrivingLicense> drivingLicenses = drivingLicenseDAO.getAllDrivingLicenses();
        licenseCategoryItems = new ArrayList<>(); // Lưu danh sách thành viên
        for (DrivingLicense license : drivingLicenses) {
            licenseCategoryItems.add(new LicenseCategoryItem(
                    license.getName(),
                    license.getCode(),
                    license.getDescription()
            ));
        }

        adapter = new LicensesAdapter(licenseCategoryItems, (position, item) -> {
            Toast.makeText(this, "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putString(KEY_SELECTED_LICENSE_CODE, item.getShortCode()).apply(); // Lưu code
            adapter.setSelectedItem(position); // Cập nhật item được chọn
        });
        recyclerView.setAdapter(adapter);

        // Khôi phục item đã chọn dựa trên code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedCode = prefs.getString(KEY_SELECTED_LICENSE_CODE, null);
        if (savedCode != null && adapter != null) {
            int position = findPositionByCode(savedCode);
            if (position != -1) {
                adapter.setSelectedItem(position);
                recyclerView.scrollToPosition(position); // Cuộn đến item được chọn
            }
        }
    }

    private int findPositionByCode(String code) {
        if (licenseCategoryItems != null && !licenseCategoryItems.isEmpty()) {
            for (int i = 0; i < licenseCategoryItems.size(); i++) {
                if (licenseCategoryItems.get(i).getShortCode().equals(code)) {
                    return i;
                }
            }
        }
        return -1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu_exit, menu);
        return true;
    }
}