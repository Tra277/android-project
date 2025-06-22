package com.example.androidproject.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.androidproject.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class QuestionBottomSheetFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho bottom sheet
        View view = inflater.inflate(R.layout.fragment_question_bottom_sheet, container, false);
        Button btnSheet = view.findViewById(R.id.btn_sheet);
        btnSheet.setOnClickListener(v -> dismiss());
        // Thêm logic cho các thành phần trong bottom sheet (nếu có)
        // Ví dụ: Button, TextView, v.v.

        return view;
    }

}
