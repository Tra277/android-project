package com.example.androidproject.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.db.DatabaseHelper;

public class MainActivity extends BaseActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        setSupportActionBar(toolbar);
        toolbar.setTitle("600 câu hỏi ôn thi GPLX");
        toolbar.setNavigationIcon(null); // ←

        toolbar.inflateMenu(R.menu.top_app_bar_menu); // menu góc phải

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
    }
}