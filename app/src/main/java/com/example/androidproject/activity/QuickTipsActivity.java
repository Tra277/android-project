package com.example.androidproject.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidproject.R;
import java.util.ArrayList;
import java.util.List;
public class QuickTipsActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_tips);

        listView = findViewById(R.id.tips_list);
        tips = new ArrayList<>();

        tips.add("🚗 Xe chữa cháy luôn được ưu tiên đi trước.");
        tips.add("🚧 Trong vòng xuyến, nhường xe bên trái.");
        tips.add("🚓 Xe cứu thương, công an, cứu hỏa luôn được ưu tiên.");
        tips.add("📘 Câu khái niệm thường chọn đáp án dài, đầy đủ.");
        tips.add("🛑 Biển cấm xe con không cấm xe máy.");
        tips.add("🏁 Khi rẽ trái tại giao lộ không có đèn, nhường xe đi thẳng.");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tips);
        listView.setAdapter(adapter);
    }
}
