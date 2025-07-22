package com.example.androidproject.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.model.DrivingLicense;

public class LicenseInfoPopup {

    private final Context context;
    private Dialog dialog;

    public LicenseInfoPopup(Context context) {
        this.context = context;
    }

    public void show(DrivingLicense license) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.popup_license_info);

        // Set license information
        TextView tvLicenseType = dialog.findViewById(R.id.tv_license_type);
        TextView tvQuestionCount = dialog.findViewById(R.id.tv_question_count);
        TextView tvTimeLimit = dialog.findViewById(R.id.tv_time_limit);
        TextView tvPassRequirement = dialog.findViewById(R.id.tv_pass_requirement);

        // Set values based on license type
        String licenseCode = license.getCode();
        tvLicenseType.setText("Hạng " + licenseCode);

        // Set question count, time limit, and pass requirement based on license type
        switch (licenseCode) {
            case "A1":
                tvQuestionCount.setText("25");
                tvTimeLimit.setText("19 phút");
                tvPassRequirement.setText("21/25");
                break;
            case "A":
                tvQuestionCount.setText("25");
                tvTimeLimit.setText("19 phút");
                tvPassRequirement.setText("23/25");
                break;
            case "B":
                tvQuestionCount.setText("30");
                tvTimeLimit.setText("27 phút");
                tvPassRequirement.setText("27/30");
                break;
            case "C1":
                tvQuestionCount.setText("35");
                tvTimeLimit.setText("22 phút");
                tvPassRequirement.setText("32/35");
                break;
            case "C":
                tvQuestionCount.setText("40");
                tvTimeLimit.setText("22 phút");
                tvPassRequirement.setText("37/40");
                break;
            default: // D1, D2, D, CE, D1E, D2E, DE
                tvQuestionCount.setText("45");
                tvTimeLimit.setText("25 phút");
                tvPassRequirement.setText("42/45");
                break;
        }

        // Close button
        Button btnClose = dialog.findViewById(R.id.btn_close_popup);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}