package com.example.androidproject.activity;
import android.widget.TextView;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidproject.R;public class QuickTipsActivity extends AppCompatActivity {

    private TextView tipsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_tips);

        tipsContent = findViewById(R.id.tips_content);

        String formattedTips =
                "<h2 style='color:#2E86C1'>📘 MẸO ghi nhớ 600 câu hỏi ôn thi GPLX</h2>" +

                        "<h3 style='color:#2874A6'>🍺 Nồng độ cồn</h3>" +
                        "<p><b>Người điều khiển xe mô tô, ô tô, máy kéo</b> trên đường mà trong máu hoặc hơi thở có nồng độ cồn: <span style='color:red'><b>Bị nghiêm cấm</b></span>.</p><hr/>" +

                        "<h3 style='color:#2874A6'>📏 Khoảng cách an toàn tối thiểu</h3>" +
                        "<ul>" +
                        "<li><b>35m</b> nếu vận tốc = 60 km/h</li>" +
                        "<li><b>55m</b> nếu 60 &lt; V ≤ 80</li>" +
                        "<li><b>70m</b> nếu 80 &lt; V ≤ 100</li>" +
                        "<li><b>100m</b> nếu 100 &lt; V ≤ 120</li>" +
                        "<li><i>Dưới 60km/h:</i> Chủ động và đảm bảo khoảng cách.</li>" +
                        "</ul><hr/>" +

                        "<h3 style='color:#2874A6'>🏍️ Các hạng GPLX mới (áp dụng từ 01/01/2025)</h3>" +
                        "<ul>" +
                        "<li><b>Hạng A1</b>: Mô tô đến 125 cm³ hoặc đến 11 kW</li>" +
                        "<li><b>Hạng A</b>: Mô tô trên 125 cm³ hoặc >11 kW</li>" +
                        "<li><b>Hạng B</b>: Ô tô đến 8 chỗ, tải ≤ 3.5 tấn, kéo rơ moóc ≤ 750 kg</li>" +
                        "<li><b>Hạng C1</b>: Tải 3.5–7.5 tấn và lái xe hạng B</li>" +
                        "<li><b>Hạng C</b>: Tải >7.5 tấn và lái xe hạng B, C1</li>" +
                        "<li>...</li>" + // Tiếp tục như vậy để rút gọn code
                        "</ul><hr/>" +

                        "<h3 style='color:#2874A6'>👴 Quy định về độ tuổi</h3>" +
                        "<p><b>Tuổi tối đa lái xe >29 chỗ:</b> Nam 57, Nữ 55</p>" +
                        "<p><b>Tuổi lấy bằng:</b></p>" +
                        "<ul>" +
                        "<li><b>Xe dưới 50 cm³</b>: 16 tuổi</li>" +
                        "<li><b>Hạng A1, A, B1, B, C1</b>: 18 tuổi</li>" +
                        "<li><b>Hạng C, BE</b>: 21 tuổi</li>" +
                        "<li><b>Hạng D1, D2, C1E, CE</b>: 24 tuổi</li>" +
                        "<li><b>Hạng D, D1E, D2E, DE</b>: 27 tuổi</li>" +
                        "</ul><hr/>" +

                        "<h3 style='color:#2874A6'>🚧 Trên đường cao tốc, hầm, vòng xuyến...</h3>" +
                        "<ul>" +
                        "<li>Không quay đầu, không lùi, không vượt</li>" +
                        "<li>Không vượt trên cầu hẹp 1 làn</li>" +
                        "<li>Không quay đầu tại phần đường dành cho người đi bộ</li>" +
                        "<li>...</li>" +
                        "</ul><hr/>" +

                        "<h3 style='color:#2874A6'>⚠️ Biển báo & Quy tắc ưu tiên</h3>" +
                        "<ul>" +
                        "<li><b>Biển nguy hiểm:</b> Tam giác vàng</li>" +
                        "<li><b>Biển cấm:</b> Vòng tròn đỏ</li>" +
                        "<li><b>Hiệu lệnh:</b> Vòng tròn xanh</li>" +
                        "<li><b>Chỉ dẫn:</b> Hình chữ nhật/vuông xanh</li>" +
                        "</ul><hr/>" +

                        "<h3 style='color:#2874A6'>🚘 Tốc độ giới hạn</h3>" +
                        "<p><b>Trong khu dân cư:</b></p>" +
                        "<ul>" +
                        "<li>Đường đôi, 1 chiều ≥2 làn: 60 km/h</li>" +
                        "<li>Đường 2 chiều, 1 chiều 1 làn: 50 km/h</li>" +
                        "</ul>" +
                        "<p><b>Ngoài khu dân cư:</b> (không tính cao tốc)</p>" +
                        "<ul>" +
                        "<li><b>Đường đôi:</b> Tối đa 90 km/h (xe con)</li>" +
                        "<li><b>Đường 2 chiều:</b> Tối đa 80 km/h (xe con)</li>" +
                        "<li><b>Xe buýt, xe tải:</b> ≤ 60–70 km/h</li>" +
                        "</ul><hr/>" +

                        "<h3 style='color:#2874A6'>📚 Quy tắc tổng quát</h3>" +
                        "<ul>" +
                        "<li>Tất cả câu có đáp án 'bị nghiêm cấm' thường là đáp án đúng</li>" +
                        "<li>Xe cơ giới KHÔNG bao gồm xe gắn máy</li>" +
                        "<li>Điểm giao cắt với đường sắt: Ưu tiên đường sắt</li>" +
                        "</ul><hr/>" +

                        "<h3 style='color:#2874A6'>🔧 Kỹ thuật & cấu tạo</h3>" +
                        "<ul>" +
                        "<li>Niên hạn ô tô tải: <b>25 năm</b></li>" +
                        "<li>Ắc quy dùng để <b>tích điện</b></li>" +
                        "<li>Ly hợp dùng để <b>ngắt truyền động</b></li>" +
                        "</ul><hr/>";








        tipsContent.setText(Html.fromHtml(formattedTips));
        tipsContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
